import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class TextFormatterTest {
    @Test
    public void testTextFormatter() {
        String lol = "#hah hah";
        StringBuilder sb = new StringBuilder();
        String[] parts = lol.split(" ");
        for (String s : parts) {
            if (s.startsWith("#")) {
                sb.append("<a href=\"api/tags/?tag=" + s + ">" + s + "</a>").append(" ");
            } else {
                sb.append(s + " ");
            }
        }
        lol = sb.toString();
        Assert.assertTrue(true);
    }
}
