
package compress;

import utils.CPriorityQueue;
import java.util.HashMap;

/**
 * Class used to compress a UTF-8 coded String to a String only
 * containing 1's and 0's using Huffman coding.
 * @author Henri Sundquist
 */
public class Huffman {
    
    private HashMap<Character, String> mapping;
    private HashMap<String, Character> demapping;
    
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
        CPriorityQueue queue = getQueue(symbolArr);
        Node root = buildTree(queue);
        traverse(root, "");
        return convert(str, root);
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
    public CPriorityQueue getQueue(int[] symbolArr) {
        CPriorityQueue queue = new CPriorityQueue();

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
    public Node buildTree(CPriorityQueue queue) {
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
        traverse(n.getLeft(), i + "0");
        traverse(n.getRight(), i + "1");
    }
    
    /**
     * Compress the UTF-8 encoded String into binary.
     * @param str UTF-8 encoded String
     * @return Binary coded String
     */
    public String convert(String str, Node root) {
        // Convert the text
        String text = "";
        for (int i = 0; i < str.length(); i++) {
            text += mapping.get(str.charAt(i));
        }
        
        // Convert the tree
        String tree = convertTree(root, "");
        
        String breakpoint = "";
        for (int i = 1; i <= 9; i++) {
            breakpoint += "1";
        }
        // Concatenate tree -> 8x 1-bits -> text
        String compressedStr = tree + breakpoint + text;
        
        return compressedStr;
    }
    
    private String convertTree(Node n, String str) {
        // Is inner-node, encode 0
        if (n.getLeft() != null && n.getRight() != null) {
            str += "0";
            // Pre-order, left first then right
            str = convertTree(n.getLeft(), str);
            str = convertTree(n.getRight(), str);
            return str;
        }
        // Is leaf, encode 1
        str += "1";
        
        // Followed by 8 bits corresponding to the UTF-8 character
        String bits = Integer.toBinaryString(n.getStr().charAt(0));
        
        // Add 0's to front to make 8 bits long if needed.
        if (bits.length() < 8) {
            int zeroAdd = 8 - bits.length();
            for (int i = 1; i <= zeroAdd; i++) {
                bits = "0" + bits;
            }
            str += bits;
        }
        
        return str;
    }
    
    public String decompress(String binStr) throws IllegalArgumentException {
        demapping = new HashMap<>();
        
        // Extract binary tree from data and build mapping
        int i = 0;
        while (true) {
            // Leaf, read next 8 bits.
            if (binStr.charAt(i) == '1') {
                i++;
                String charCode = "";
                for (int subI = 1; subI <= 8; subI++) {
                    charCode += binStr.charAt(i);
                    i++;
                }
                // Check if next 8 bits are the separator
                if (charCode.equals("11111111")) {
                    String tree = "";
                    for (int b = 0; b < i - 9; b++) {
                        tree += binStr.charAt(b);
                    }
                    buildMapping(tree, 0, "");
                    break;
                }
            } else {
                i++;
            }
        }
        
        // i is already past the 9-bit separator
        // Extract rest of the data
        String str = "";
        for (int d = i; d < binStr.length(); d++) {
            str += binStr.charAt(d);
        }
        return mapBinaryStr(str);
    }
    
    private int buildMapping(String tree, int i, String path) {
        char bin = tree.charAt(i);
        i++;
        
        // Leaf, extract next 8 bits.
        if (bin == '1') {
            int byteEnd = i + 8;
            String Char = "";
            while (i < byteEnd) {
                Char += tree.charAt(i);
                i++;
            }
            demapping.put(path, (char) getByteValue(Char));
            return i;
        } else if (bin == '0') {
            i = buildMapping(tree, i, path + "0");
            i = buildMapping(tree, i, path + "1");
        }
        return i;
    }
    
    private String mapBinaryStr(String binStr) {
        String str = "";
        String subStr = "";
        
        for (int i = 0; i < binStr.length(); i++) {
            subStr += binStr.charAt(i);
            if (demapping.keySet().contains(subStr)) {
                str += demapping.get(subStr);
                subStr = "";
            }
        }
        return str;
    }
    
    public int getByteValue(String str) {
        int res = 0; 
        int val = 128;
        
        for (int i = 0; i <= 7; i++) {
            if (str.charAt(i) == '1') {
                res += val;
            }
            val = val / 2;
        }
        return res;
    }
}
