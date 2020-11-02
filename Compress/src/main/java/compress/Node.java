
package compress;


public class Node implements Comparable<Node> {
    
    private String str;
    private int count;
    
    public Node(String str, int count) {
        this.str = str;
        this.count = count;
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
