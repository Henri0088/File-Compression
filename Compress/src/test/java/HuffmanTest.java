
import compress.Huffman;
import io.InputReader;
import utils.Node;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import utils.CPriorityQueue;


public class HuffmanTest {
    
    private Huffman huffman;
    private InputReader reader;
    
    @Before
    public void setUpClass() {
        huffman = new Huffman();
        try {
            reader = new InputReader("alice29.txt");
        } catch (Exception e) {
            System.out.println("reading failed.");
        }
    }
    
    @Test
    public void testGetCounts1() {
        String testStr = "abcabcab";
        int[] counts = huffman.getCounts(testStr);
        
        assertTrue(counts[97] == 3 && counts[98] == 3 && counts[99] == 2);
    }
    
    @Test
    public void testGetCounts2() {
        String testStr = " \n";
        int[] counts = huffman.getCounts(testStr);
        
        assertTrue(counts[10] == 1 && counts[32] == 1);
    }
    
    @Test
    public void testGetCounts3() {
        String testStr = "aaAA";
        int[] counts = huffman.getCounts(testStr);
        
        assertTrue(counts[97] == 2 && counts[65] == 2);
    }
    
    @Test
    public void queueCorrectOrder() {
        int[] counts = new int[256];
        counts[97] = 2;
        counts[98] = 1;
        counts[99] = 3;
        
        CPriorityQueue queue = huffman.getQueue(counts);
        Node n1 = queue.poll();
        Node n2 = queue.poll();
        Node n3 = queue.poll();
        
        System.out.println("n1: " + n1 + " n2: " + n2 + " n3: " + n3);
        
        assertTrue(n1.getStr().equals("b") && n2.getStr().equals("a") && n3.getStr().equals("c"));
    }
    
    @Test
    public void testTree() {
        String testStr = "aaabbc";
        int[] counts = huffman.getCounts(testStr);
        CPriorityQueue queue = huffman.getQueue(counts);
        
        Node root = huffman.buildTree(queue);
        assertTrue(root.getStr().equals("acb"));
        
        Node n1 = root.getRight();
        assertTrue(n1.getStr().equals("cb") && n1.getCount() == 3);
        
        Node n2 = n1.getRight();
        assertTrue(n2.getStr().equals("b") && n2.getCount() == 2);
    }
    
    @Test
    public void testHuffmanSmall() {
        String testStr = "aaabbc";
        String binStr = huffman.compress(testStr);
        String str = huffman.decompress(binStr);
        assertTrue(str.equals(testStr));
    }
    
    @Test
    public void testHuffmanMedium() {
        String str = reader.readLines();
        String compressedStr = huffman.compress(str);
        assertTrue(str.equals(huffman.decompress(compressedStr)));
    }
    
}
