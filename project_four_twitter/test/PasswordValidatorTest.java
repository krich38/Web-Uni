import org.junit.Assert;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class PasswordValidatorTest {
    @Test
    public void testPasswordValidation() {
        String password = "Yamaha@38!";
        String encrypted = BCrypt.hashpw(password, BCrypt.gensalt());

        Assert.assertTrue(BCrypt.checkpw(password, encrypted));
    }
}
