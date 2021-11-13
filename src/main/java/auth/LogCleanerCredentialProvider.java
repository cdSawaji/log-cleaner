package auth;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements {@link AWSCredentialsProvider}
 */
public class LogCleanerCredentialProvider implements AWSCredentialsProvider {
    @Override
    public AWSCredentials getCredentials() {
        return new LogCleanerCredentials();
    }

    @Override
    public void refresh() {
        // TODO: Add an implementation
    }

    /**
     * @param bucketName Name of the bucket to be accessed
     *
     * @return true IFF the bucket is supported, false otherwise
     */
    public static boolean supports(String bucketName) {
        return SUPPORTED_BUCKETS.contains(bucketName);
    }

    private static final Set<String> SUPPORTED_BUCKETS = Set.of("stellar.health.test.chinmay.sawaji");
}
