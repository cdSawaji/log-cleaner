package auth;

import com.amazonaws.auth.AWSCredentials;

/**
 * Implements {@link AWSCredentials}
 */
public class LogCleanerCredentials implements AWSCredentials {

    @Override
    public String getAWSAccessKeyId() {
        return "AKIARX7DSUMEJMD2PBML";
    }

    @Override
    public String getAWSSecretKey() {
        return "obs3y63nSgP9I0ZKNtx2Re/U4WJd+Q5UYVnyYAwh";
    }
}
