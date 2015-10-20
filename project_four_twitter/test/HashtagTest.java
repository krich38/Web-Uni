import model.Message;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class HashtagTest {
    @Test
    public void testHashtagRegex() {
        String text = "This is an #example of a #hashtag #hashtag #post.";
        Set<String> tags = Message.extractTags(text);
        Assert.assertTrue(tags.containsAll(Arrays.asList("post", "example", "hashtag")));
    }
}
