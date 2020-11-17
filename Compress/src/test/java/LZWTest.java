
import compress.InputReader;
import compress.LZW;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


public class LZWTest {
    
    private LZW lzw;
    private InputReader reader;
    
    @Before
    public void setUpClass() {
        lzw = new LZW();
        reader = new InputReader("test.txt");
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
        assertTrue(str.equals(lzw.decompress(str)));
    }
    
}
