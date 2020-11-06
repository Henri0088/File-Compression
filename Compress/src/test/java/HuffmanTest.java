
import compress.Huffman;
import compress.Node;
import java.util.PriorityQueue;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


public class HuffmanTest {
    
    private Huffman huffman;
    
    public HuffmanTest() {
    }
    
    @Before
    public void setUpClass() {
        huffman = new Huffman();
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
        
        PriorityQueue<Node> queue = huffman.getQueue(counts);
        Node n1 = queue.poll();
        Node n2 = queue.poll();
        Node n3 = queue.poll();
        
        assertTrue(n1.getStr().equals("b") && n2.getStr().equals("a") && n3.getStr().equals("c"));
    }
    
    @Test
    public void testTree() {
        String testStr = "aaabbc";
        int[] counts = huffman.getCounts(testStr);
        PriorityQueue<Node> queue = huffman.getQueue(counts);
        
        Node root = huffman.buildTree(queue);
        assertTrue(root.getStr().equals("acb"));
        
        Node n1 = root.getRight();
        assertTrue(n1.getStr().equals("cb") && n1.getCount() == 3);
        
        Node n2 = n1.getRight();
        assertTrue(n2.getStr().equals("b") && n2.getCount() == 2);
    }
    
    @Test 
    public void testHuffman() {
        String testStr = "aaabbc";
        String binStr = huffman.compress(testStr);
        assertTrue(binStr.equals("000111110"));
    }
    
    @Test
    public void testHuffmanDecompression() {
        String testStr = "aaabbc";
        String binStr = huffman.compress(testStr);
        String str = huffman.decompress(binStr);
        assertTrue(str.equals(testStr));
    }
}