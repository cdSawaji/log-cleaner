package auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.CredentialNotFoundException;

public class LogCleanerCredentialsTest {

    @Test
    public void smokeTest()  {
        LogCleanerCredentials instance = new LogCleanerCredentials();
        Assertions.assertEquals("AKIARX7DSUMEJMD2PBML", instance.getAWSAccessKeyId());
        Assertions.assertEquals("obs3y63nSgP9I0ZKNtx2Re/U4WJd+Q5UYVnyYAwh", instance.getAWSSecretKey());
    }
}
