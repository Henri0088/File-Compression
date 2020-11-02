package compress;

import java.io.*;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        InputReader reader = new InputReader("test.txt");
        
        Huffman huffman = new Huffman(reader);
    }
    
}
