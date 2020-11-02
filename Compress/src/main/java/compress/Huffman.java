
package compress;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Huffman {
    
    private IOReader reader;
    
    public Huffman(IOReader reader) {
        this.reader = reader;
    }
    
    public String compress() {
        PriorityQueue<Node> nodeQueue = getQueue();
        
        return "Test";
    }
    
    public int[] getCounts() {
        String str = reader.readLines();
        
        // Have "_" at index 0, the rest 26 letters at spots 1 - 26 and linebreak at spot 27
        int[] symbolArr = new int[28];
        
        // Count symbols
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            switch (symbol) {
                case 95:
                    symbolArr[0]++;
                    break;
                case 32:
                    symbolArr[27]++;
                    break;
                default:
                    symbolArr[symbol - 96]++;
                    break;
            }
        }
        System.out.println(Arrays.toString(symbolArr));
        return symbolArr;
    }
    
    public PriorityQueue<Node> getQueue() {
        int[] symbolArr = getCounts();
        PriorityQueue<Node> queue = new PriorityQueue<>();
        
        Node underScoreNode = new Node("_", symbolArr[0]);
        queue.add(underScoreNode);
        
        Node whiteSpaceNode = new Node(" ", symbolArr[27]);
        queue.add(whiteSpaceNode);
        
        // Loop over chars a-z, a = 97 in unicode
        for (char i = 1+96; i <= 26+96; i++) {
            Node node = new Node(String.valueOf(i), symbolArr[i - 96]);
            queue.add(node);
        }
        
        return queue;
    }
    
}
