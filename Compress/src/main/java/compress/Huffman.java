
package compress;

import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Class used to compress a UTF-8 coded String to a String only
 * containing 1's and 0's using Huffman coding.
 * @author Henri Sundquist
 */
public class Huffman {
    
    private HashMap<Character, String> mapping;
    
    public Huffman() {
    }
    
    /**
     * Method which calls all the necessary subroutines in 
     * order to compress a string.
     * @param str UTF-8 coded string
     * @return Huffman coded string
     */
    public String compress(String str) {
        mapping = new HashMap<>();
        int[] symbolArr = getCounts(str);
        PriorityQueue<Node> queue = getQueue(symbolArr);
        Node root = buildTree(queue);
        traverse(root, "");
        return convert(str);
    }
    
    /**
     * Extract the counts of individual characters.
     * @param str UTF-8 encoded string
     * @return Array of size 256 with the counts of UTF-8 characters
     */
    public int[] getCounts(String str) {
        int[] symbolArr = new int[256];
        
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            symbolArr[symbol]++;
        }
        return symbolArr;
    }
    
    /**
     * Initialize a PriorityQueue containing a Node for each
     * character which is present in the given array.
     * @param symbolArr Array of size 256
     * @return PriorityQueue of Nodes
     */
    public PriorityQueue<Node> getQueue(int[] symbolArr) {
        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (char i = 1; i < 256; i++) {
            if (symbolArr[i] == 0) {
                continue;
            }
            Node node = new Node(Character.toString((char) i), symbolArr[i]);
            queue.add(node);
        }
        return queue;
    }
    
    /**
     * Use Nodes to build a binary tree.
     * @param queue PriorityQueue of Nodes
     * @return Root Node of the binary tree
     */
    public Node buildTree(PriorityQueue<Node> queue) {
        while (!queue.isEmpty()) {
            Node n1 = queue.poll();
            Node n2 = queue.poll();
            
            if (n2 == null) {
                return n1;
            }
            
            String s = n1.getStr() + n2.getStr();
            int i = n1.getCount() + n2.getCount();
            Node parentNode = new Node(s, i);
            
            n1.setParent(parentNode);
            n2.setParent(parentNode);
            
            parentNode.setLeft(n1.getCount() <= n2.getCount() ? n1 : n2);
            parentNode.setRight(n1.getCount() > n2.getCount() ? n1 : n2);
            queue.add(parentNode);
        }
        return null;
    }
    
    /**
     * Recursively traverses a binary tree of Nodes and inserts
     * character/binary pairs into a HashMap upon reaching a leaf. 
     * The binary codes for characters are determined by their position in the binary tree. 
     * The root Node doesn't have a binary code, 
     * after that the left child will have the parents binary code + "0"
     * and the right child will have the parents binary code + "1".
     * @param n Node to check next (First call should be to the root Node)
     * @param i The current binary code of Node n
     */
    public void traverse(Node n, String i) {
        if (n.getLeft() == null && n.getRight() == null) {
            
            mapping.put(n.getStr().charAt(0), i);
            return;
        }
        if (n.getLeft() != null) {
            traverse(n.getLeft(), i + "0");
        }
        if (n.getRight() != null) {
            traverse(n.getRight(), i + "1");
        }
    }
    
    /**
     * Convert a UTF-8 encoded string to binary code
     * according to the mapping.
     * @param str UTF-8 encoded String
     * @return Binary coded String
     */
    public String convert(String str) {
        String compressedStr = "";
        for (int i = 0; i < str.length(); i++) {
            compressedStr += mapping.get(str.charAt(i));
        }
        return compressedStr;
    }  
}
