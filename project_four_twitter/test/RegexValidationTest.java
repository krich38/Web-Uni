import controllers.Application;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class RegexValidationTest {
    @Test
    public void testRegex() {
        String user = "Foo123@hotmail.com";
        Matcher matcher1 = Application.EMAIL_PATTERN.matcher(user);

        Assert.assertEquals(true, matcher1.matches());

    }

}
