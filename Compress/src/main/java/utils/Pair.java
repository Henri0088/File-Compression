
package utils;

public class Pair {
    
    private String key;
    private int value;
    private Pair next;
    private Pair previous;
    
    public Pair(String key, int value) {
        this.key = key;
        this.value = value;
    }
    
    public String getKey() {
        return key;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setNext(Pair nextPair) {
        next = nextPair;
    }
    
    public void setPrevious(Pair previousPair) {
        previous = previousPair;
    }
    
    public Pair getNext() {
        return next;
    }
    
    public Pair getPrevious() {
        return previous;
    }
    
}
