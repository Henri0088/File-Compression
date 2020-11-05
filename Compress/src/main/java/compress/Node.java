
package compress;

/**
 * Class which is used while constructing and traversing
 * the binary tree.
 * @author henri
 */
public class Node implements Comparable<Node> {
    
    private String str;
    private int count;
    private Node parent;
    private Node left;
    private Node right;
    
    public Node(String str, int count) {
        this.str = str;
        this.count = count;
        this.parent = this;
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public void setLeft(Node left) {
        this.left = left;
    }
    
    public void setRight(Node right) {
        this.right = right;
    }
    
    public Node getLeft() {
        return left;
    }
    
    public Node getRight() {
        return right;
    }
    
    public int getCount() {
        return count;
    }
    
    public String getStr() {
        return str;
    }
    
    @Override
    public int compareTo(Node node2) {
        return count - node2.getCount();
    }
}
