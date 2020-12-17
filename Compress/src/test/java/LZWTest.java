
import io.InputReader;
import compress.LZW;
import java.util.Random;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


public class LZWTest {
    
    private LZW lzw;
    private InputReader reader;
    
    @Before
    public void setUpClass() {
        lzw = new LZW();
        try {
            reader = new InputReader("alice29.txt");
        } catch (Exception e) {
            System.out.println("reading failed.");
        }
    }
    
    @Test
    public void testLZWSmall() {
        String cStr = lzw.compress("TOBEORNOTTOBEORTOBEORNOT");
        String dStr = lzw.decompress(cStr);
        assertTrue("TOBEORNOTTOBEORTOBEORNOT".equals(dStr));
    }
    
    @Test
    public void testLZWMedium() {
        String str = reader.readLines();
        String cStr = lzw.compress(str);
        String dStr = lzw.decompress(cStr);
        assertTrue(str.equals(dStr));
    }
    
    @Test
    public void randTest() {
        StringBuilder str;
        Random rnd = new Random();
        for (int r = 0; r < 5; r++) {
            str = new StringBuilder();
            lzw = new LZW();
            for (int i = 0; i < 10_000; i++) {
                str.append((char) rnd.nextInt(256));
            }
            String original = str.toString();
            String uncompressed = lzw.decompress(lzw.compress(original));
            assertTrue(original.equals(uncompressed));
        }
    }
    
}
