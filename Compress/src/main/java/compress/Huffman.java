
package compress;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Huffman {
    
    private HashMap<Character, String> mapping;
    
    public Huffman() {
        this.mapping = new HashMap<>();
    }
    
    public String compress(String str) {
        int[] symbolArr = getCounts(str);
        PriorityQueue<Node> queue = getQueue(symbolArr);
        Node root = buildTree(queue);
        traverse(root, "");
        
        return convert(str);
    }
    
    public int[] getCounts(String str) {
        
        // Each symbol at their respective UTF-8 index
        int[] symbolArr = new int[256];
        
        // Count symbols
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            symbolArr[symbol]++;
        }
        return symbolArr;
    }
    
    public PriorityQueue<Node> getQueue(int[] symbolArr) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
  
        // Loop over chars in UTF-8
        for (char i = 1; i < 256; i++) {
            if (symbolArr[i] == 0) {
                continue;
            }
            Node node = new Node(Character.toString((char)i), symbolArr[i]);
            queue.add(node);
        }
        return queue;
    }
    
    public Node buildTree(PriorityQueue<Node> queue) {
        while (!queue.isEmpty()) {
            Node n1 = queue.poll();
            Node n2 = queue.poll();
            
            if (n2 == null) {
                // There was only 1 node in the queue, this must be the final root
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
    
    public void traverse(Node n, String i) {
        if (n.getLeft() == null && n.getRight() == null) {
            
            mapping.put(n.getStr().charAt(0), i);
            return;
        }
        if (n.getLeft() != null) {
            traverse(n.getLeft(), i+"0");
        }
        if (n.getRight() != null) {
            traverse(n.getRight(), i+"1");
        }
    }
    
    public String convert(String str) {
        String compressedStr = "";
        for (int i = 0; i < str.length(); i++) {
            compressedStr += mapping.get(str.charAt(i));
        }
        return compressedStr;
    }  
}
