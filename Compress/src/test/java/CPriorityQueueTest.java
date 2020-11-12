
import compress.Node;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import utils.CPriorityQueue;
import java.util.Random;


public class CPriorityQueueTest {
    
    private CPriorityQueue queue;
    
    @Before
    public void setUpClass() {
        this.queue = new CPriorityQueue();
    }
    
    @Test
    public void basicInOutTest() {
        queue.add(new Node("a", 1));
        queue.add(new Node("b", 2));
        queue.add(new Node("c", 3));
        
        String a = queue.poll().getStr();
        String b = queue.poll().getStr();
        String c = queue.poll().getStr();
        assertTrue(a.equals("a") && b.equals("b") && c.equals("c"));
    }
    
    @Test
    public void reverseInOutTest() {
        queue.add(new Node("a", 10));
        queue.add(new Node("b", 5));
        queue.add(new Node("c", 1));
        
        String c = queue.poll().getStr();
        String b = queue.poll().getStr();
        String a = queue.poll().getStr();
        assertTrue(a.equals("a") && b.equals("b") && c.equals("c"));
    }
    
    @Test
    public void rand100Test() {
        Random rnd = new Random();
        for (int i = 0; i < 100; i++) {
            queue.add(new Node(String.valueOf((char) i), rnd.nextInt(500)));
        }
        
        int prevNode = queue.poll().getCount();
        while (!queue.isEmpty()) {
            int node = queue.poll().getCount();
            assertTrue(prevNode <= node);
            prevNode = node;
        }
    }
    
}
