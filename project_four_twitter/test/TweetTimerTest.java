import model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class TweetTimerTest {
    @Test
    public void testTweetTimer() throws InterruptedException {
        User user = new User("Foo", "email@hotmail.com", "password");
        boolean firstTest = user.canPost(); // should be true
        user.updatePosted();
        boolean secondTest = user.canPost(); // should be false
        Thread.sleep(TimeUnit.SECONDS.toMillis(8));
        boolean thirdTest = user.canPost(); // should be true
        Assert.assertEquals(true, firstTest);
        Assert.assertEquals(false, secondTest);
        Assert.assertEquals(true, thirdTest);
    }
}
