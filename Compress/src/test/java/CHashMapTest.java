
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import utils.CHashMap;


public class CHashMapTest {

    private CHashMap map;
    
    @Before
    public void setUpClass() {
        this.map = new CHashMap();
    }
    
    @Test
    public void binaryStrTest() {
        map.put("1001101010", 15);
        map.put("1001111010", 67);
        
        int fifteen = map.get("1001101010");
        int sixtySeven = map.get("1001111010");
        
        assertTrue(fifteen == 15);
        assertTrue(sixtySeven == 67);
    }
    
    @Test
    public void keySetTest() {
        map.put("abc", 1);
        map.put("kpk", 2);
        map.put("757", 3);
        
        assertTrue(map.containsKey("abc"));
        assertTrue(map.containsKey("kpk"));
        assertTrue(map.containsKey("757"));
    }
}
