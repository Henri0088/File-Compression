
package compress;

import utils.Node;
import utils.CPriorityQueue;
import utils.CHashMap;

/**
 * Class used to compress a UTF-8 coded String using Huffman coding.
 * @author Henri Sundquist
 */
public class Huffman {
    
    private String[] mapping;
    private CHashMap demapping;
    
    public Huffman() {
    }
    
    /**
     * Method which calls all the necessary subroutines in 
     * order to compress a string.
     * @param str UTF-8 coded string
     * @return Huffman coded string
     */
    public String compress(String str) {
        mapping = new String[256];
        int[] symbolArr = getCounts(str);
        CPriorityQueue queue = getQueue(symbolArr);
        Node root = buildTree(queue);
        getCodes(root, "");
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
     * Initialize a CPriorityQueue containing a Node for each
     * character which is present in the given array.
     * @param symbolArr Array of size 256
     * @return CPriorityQueue of Nodes
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
     * @param queue CPriorityQueue of Nodes
     * @return Root Node of the binary tree
     */
    public Node buildTree(CPriorityQueue queue) {
        int r = 0;
        while (!queue.isEmpty()) {
            r++;
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
     * @param n Node to check next (First call should be to the root Node)
     * @param i The current binary code of Node n (First call should be a empty String)
     */
    public void getCodes(Node n, String i) {
        if (n.getLeft() == null && n.getRight() == null) {
            mapping[(int) n.getStr().charAt(0)] = i;
            return;
        }
        getCodes(n.getLeft(), i + "0");
        getCodes(n.getRight(), i + "1");
    }
    
    /**
     * Compress the UTF-8 encoded String into binary.
     * @param str UTF-8 encoded String
     * @param root root of the Huffman encoding tree
     * @return Binary coded String
     */
    public String convert(String str, Node root) {
        StringBuilder builder = new StringBuilder();
        // Convert the text
        for (int i = 0; i < str.length(); i++) {
            builder.append(mapping[(int) str.charAt(i)]);
        }
        String text = builder.toString();
        
        // Convert the tree
        String tree = convertTree(root, "");
        
        // Concatenate tree -> separator -> text
        String compressedStr = tree + "111111111" + text;
        
        return compressedStr;
    }
    
    private String convertTree(Node n, String str) {
        // Is inner-node, encode 0
        if (n.getLeft() != null && n.getRight() != null) {
            str += "0";
            // Convert subtrees in pre-order
            str = convertTree(n.getLeft(), str);
            str = convertTree(n.getRight(), str);
            return str;
        }
        // Is leaf concatenate 1
        str += "1";
        
        // Followed by 8 bits corresponding to the UTF-8 character
        String bits = Integer.toBinaryString(n.getStr().charAt(0));
        
        // Add 0's to front to make character code 8 bits long if needed.
        if (bits.length() < 8) {
            int zeroAdd = 8 - bits.length();
            for (int i = 1; i <= zeroAdd; i++) {
                bits = "0" + bits;
            }
            str += bits;
        }
        return str;
    }
    
    /**
     * Decompress a Huffman encoded binary string.
     * To achieve correct results the binary string must be
     * in the correct format. UTF-8 Strings compressed with this class
     * are in such a format.
     * @param binStr Binary String
     * @return Decompressed UTF-8 String.
     */
    public String decompress(String binStr) {
        demapping = new CHashMap();
        
        // Extract binary tree from data and build mapping
        String tree = getTree(binStr);
        buildMapping(tree, 0, "");
        
        // Jump over tree and 9-bit separator
        int i = tree.length() + 9;
        
        return mapBinaryStr(binStr.substring(i));
    }
    
    private String getTree(String binStr) {
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
                    return tree;
                }
            } else {
                i++;
            }
        }
    }
    
    private int buildMapping(String tree, int i, String path) {
        char bin = tree.charAt(i);
        i++;
        
        // Leaf, extract next 8 bits.
        if (bin == '1') {
            int byteEnd = i + 8;
            String charBits = "";
            while (i < byteEnd) {
                charBits += tree.charAt(i);
                i++;
            }
            demapping.put(path, getByteValue(charBits));
            return i;
        } else if (bin == '0') {
            i = buildMapping(tree, i, path + "0");
            i = buildMapping(tree, i, path + "1");
        }
        return i;
    }
    
    private String mapBinaryStr(String binStr) {
        String subStr = "";
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < binStr.length(); i++) {
            subStr += binStr.charAt(i);
            if (demapping.containsKey(subStr)) {
                builder.append((char) demapping.get(subStr));
                subStr = "";
            }
        }
        return builder.toString();
    }
    
    private int getByteValue(String str) {
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
