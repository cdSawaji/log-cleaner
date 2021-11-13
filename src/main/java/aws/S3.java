package aws;

import auth.LogCleanerCredentialProvider;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class S3 {

    public static S3 getInstance(String bucketName) throws CredentialNotFoundException {
        if (!LogCleanerCredentialProvider.supports(bucketName)) {
            throw new CredentialNotFoundException("No credentials found for bucket name" + bucketName);
        }

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new LogCleanerCredentialProvider())
                .build();
        return new S3(s3Client, bucketName);
    }

    public Set<String> getKeys(String prefix) {
        ListObjectsV2Result result = s3Client.listObjectsV2(bucketName, prefix);
        Set<String> keys = new HashSet<>();
        if (result.getObjectSummaries() != null) {
            for (S3ObjectSummary summary : result.getObjectSummaries()) {
                keys.add(summary.getKey());
            }
        }
        return keys;
    }

    public S3Object getObject(String key) {
        return s3Client.getObject(bucketName, key);
    }

    public void put(String key, InputStream payload, ObjectMetadata metadata) {
        PutObjectRequest request = new PutObjectRequest(bucketName, key, payload, metadata);
        s3Client.putObject(request);
    }

    private S3(AmazonS3 s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    private final AmazonS3 s3Client;
    private final String bucketName;
}
