package compress;

import IO.InputReader;
import IO.BitWriter;
import IO.BinaryInputReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        InputReader reader = new InputReader("test.txt");
        String str = reader.readLines();
        
        /*
        Huffman huff = new Huffman();
        String cStr = huff.compress(str);
        System.out.println("Compressed: " + cStr.substring(0, 50));
        String dStr = huff.decompress(cStr);
        System.out.println("Decompressed: " + dStr.subSequence(0, 50));
        */
        
        /*
        LZW lzw = new LZW();
        String cStr = lzw.compress(str);
        System.out.println("Compressed (lzw): " + cStr.substring(0, 50));
        String dStr = lzw.decompress(cStr);
        System.out.println("Decompressed (lzw): " + dStr.subSequence(0, 50));
        */
        
        UI();
    }
    
    public static void UI() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter 1 (compress) or 2 (decompress) or anything else (exit): ");
        int c = Integer.valueOf(reader.nextLine());
        switch (c) {
            case 1:
                compressUI(reader);
                break;
            case 2:
                decompressUI(reader);
                break;
        }
    }
    
    public static void compressUI(Scanner reader) {
        System.out.println("Please enter source file \n(or '1' to use default file test.txt): ");
        String file = reader.nextLine();
        
        if (file.equals("1")) {
            file = "test.txt";
        }
        
        InputReader fileReader = new InputReader(file);
        
        System.out.println("Compress using: Huffman (1) or LZW (2)? (Enter 1 or 2)");
        int choice = Integer.valueOf(reader.nextLine());
        
        String str = fileReader.readLines();
        if (choice == 1) {
            Huffman huff = new Huffman();
            String cStr = huff.compress(str);
            BitWriter writer = new BitWriter("HuffCompressed");
            writer.writeBits(cStr);
            System.out.println("Compression success, file 'HuffCompressed' created.");
        } else if (choice == 2) {
            LZW lzw = new LZW();
            String cStr = lzw.compress(str);
            BitWriter writer = new BitWriter("LZWCompressed");
            writer.writeBits(cStr);
            System.out.println("Compression success, file 'LZWCompressed' created.");
        }
    }
    
    public static void decompressUI(Scanner reader) {
        System.out.println("Decompress 'HuffCompressed' (1) or 'LZWCompressed' (2)?");
        int choice = Integer.valueOf(reader.nextLine());
        
        if (choice == 1) {
            BinaryInputReader fileReader = new BinaryInputReader("HuffCompressed");
            String cStr = fileReader.readLines();
            Huffman huff = new Huffman();
            String Str = huff.decompress(cStr);
            BitWriter writer = new BitWriter("HuffDecompressed");
            writer.writeBits(Str);
            System.out.println("Decompression success, file 'HuffCompressed' created");
            System.out.println("First 30 characters:");
            System.out.println(Str.substring(0, 30));
        } else if (choice == 2) {
            BinaryInputReader fileReader = new BinaryInputReader("LZWCompressed");
            String cStr = fileReader.readLines();
            LZW lzw = new LZW();
            String Str = lzw.decompress(cStr);
            BitWriter writer = new BitWriter("LZWDecompressed");
            writer.writeBits(Str);
            System.out.println("Decompression success, file 'LZWCompressed' created");
            System.out.println("First 30 characters:");
            System.out.println(Str.substring(0, 30));
        }
    }
    
}
