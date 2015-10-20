import model.Message;
import model.MessageStore;
import model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class TweetListTest {
    @Test
    public void testTweetList() throws InterruptedException {
        for (int i = 1; i < 10; i++) {
            Message tweet = new Message("Tweet " + i + " #tweet" + i, new User("@user" + i, "foo@foo.foo", "foopass"));
            MessageStore.prependTweet(tweet);
        }

        Message toSearchFor = new Message("#find", new User("@foo", "foofoo@foo.foo", "pass"));
        MessageStore.prependTweet(toSearchFor);

        List<Message> foundByUser = MessageStore.searchByUsername("@foo");
        List<Message> foundByTag = MessageStore.searchByHashtag("find");
        Assert.assertEquals(1, foundByUser.size()); // we should only find 1 message
        Assert.assertEquals(1, foundByTag.size()); // again, should only find 1 message


    }
}
