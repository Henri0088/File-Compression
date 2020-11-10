
package utils;

import compress.Node;
import java.util.Arrays;

public class CPriorityQueue {
    
    private Node[] arr;
    private int heapSize;
    
    public CPriorityQueue() {
        this.arr = new Node[65];
        this.arr[0] = new Node("", 0);
        this.heapSize = 0;
    }
    
    /**
     * Adds a node to the heap.
     * Also fixes the heap property.
     * @param node Node to be added.
     */
    public void add(Node node) {
        heapSize++;
        int index = heapSize;
        arr[index] = node;
        
        while (arr[index / 2].getCount() > arr[index].getCount()) {
            Node child = arr[index];
            arr[index] = arr[index / 2];
            arr[index / 2] = child;
            
            if (index / 2 == 1) {
                break;
            }
            index = index / 2;
        }
        
        // Increase array size if array is getting full
        if (heapSize > arr.length * 0.5) {
            increaseSize();
        }
    }
    
    /**
     * Retrieves and removes the smallest node from the heap.
     * Also fixes the heap property.
     * @return Node
     */
    public Node poll() {
        Node node = arr[1];
        
        // Move last to top
        int index = heapSize;
        arr[1] = arr[index];
        arr[index] = null;
        
        heapSize--;
        fixHeap(1);
        
        // TODO: Decrease arr size ?
        
        return node;
    }

    private void fixHeap(int index) {
        int leftI = 2 * index;
        int rightI = 2 * index + 1;
        
        int smI;
        if (leftI <= heapSize && arr[leftI].getCount() < arr[index].getCount()) {
            smI = leftI;
        } else {
            smI = index;
        }
        if (rightI <= heapSize && arr[rightI].getCount() < arr[smI].getCount()) {
            smI = rightI;
        }
        
        if (smI != index) {
            Node temp = arr[index];
            arr[index] = arr[smI];
            arr[smI] = temp;
            fixHeap(smI);
        }
    }
    
    /**
     * Returns True if heap is empty and false if heap
     * contains nodes.
     * @return Boolean
     */
    public boolean isEmpty() {
        return heapSize == 0;
    }
    
    private void increaseSize() {
        int newSize = arr.length * 2;
        Node[] newArr = new Node[newSize];
        System.arraycopy(arr, 1, newArr, 1, arr.length - 1);
        arr = newArr;
    }    
}
