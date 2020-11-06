
package utils;

import compress.Node;
import java.util.Arrays;

public class CPriorityQueue {
    
    private Node[] arr;
    
    public CPriorityQueue() {
        this.arr = new Node[65];
        this.arr[0] = new Node("", 0);
    }
    
    public void add(Node node) {
        int index = findLast() + 1;
        arr[index] = node;
        
        while (arr[index/2].getCount() > arr[index].getCount()) {
            Node child = arr[index];
            arr[index] = arr[index/2];
            arr[index/2] = child;
            index = index/2;
        }
        
        // If the first index is getting close to array length,
        // increase array
        if (index > arr.length*0.85) {
            increaseSize();
        }
    }
    
    public Node poll() {
        Node node = arr[1];
        
        // Move last to top
        int index = findLast();
        arr[1] = arr[index];
        arr[index] = null;
        
        // Fix heap rule
        
        return node;
    }
    
    private void increaseSize() {
        int newSize = arr.length*2;
        Node[] newArr = new Node[newSize];
        System.arraycopy(arr, 1, newArr, 1, arr.length-1);
        arr = newArr;
    }
    
    private int findLast() {
        // Find first index which is not empty.
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == null) {
                return i - 1;
            }
        }
        return 0;
    }
    
    
}
