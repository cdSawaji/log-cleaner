import aws.S3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import exceptions.InvalidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LogCleaner {

    private static void cleanLogs(S3 s3Client, String prefix) throws IOException {
        Set<String> keys = s3Client.getKeys(prefix);

        for (String key : keys) {
            System.out.println("Downloading data for Key: " + key);
            S3Object object = s3Client.getObject(key);
            InputStream updatedLogs = replaceDates(object.getObjectContent());
            // s3Client.put(key, updatedLogs, object.getObjectMetadata());
            // System.out.println("Updated the logs");
        }
    }

    private static InputStream replaceDates(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        return null;
    }

    // TODO: Regex validation
    private static String[] getTokens(String logLocation)
            throws InvalidInputException{
        System.out.println("Getting bucket name from log location: " + logLocation);
        String[] tokens = logLocation.split("/");
        if (tokens.length < 3) {
            throw new InvalidInputException(String.format("Wrong log location format: %s", logLocation));
        }
        return tokens;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Log clean-up!" + args.length);
        if (args == null || args.length == 0) {
            System.out.println("Please provide log location in the format s3.amazonaws.com/{bucket_name}/{prefix}");
        }
        String logLocation = args[0];
        System.out.println("Got log location: " + logLocation);

        String[] tokens = getTokens(logLocation);
        String bucketName = tokens[1];
        String prefix = tokens[2];
        System.out.println(String.format("Found bucket name: %s", bucketName));

        System.out.println("Preparing to fetch logs from S3");
        S3 s3Client = S3.getInstance(bucketName);

        cleanLogs(s3Client, prefix);
        System.out.println("Logs have been cleaned at location: " + logLocation);
    }
}
