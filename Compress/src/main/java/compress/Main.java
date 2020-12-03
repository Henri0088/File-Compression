package compress;

import io.BinaryInputReader;
import io.BitWriter;
import io.InputReader;
import io.Writer;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose one: \n(1): Performance test \n(2): Compress \n(3): Decompress");
        int option = Integer.valueOf(scanner.nextLine());
        
        switch (option) {
            case 1:
                performance();
                break;
            case 2:
                compress();
                break;
            case 3:
                decompress();
                break;
            default:
                break;
        }
    }
    
    public static void decompress() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give filename: ");
        String file = scanner.nextLine();
        
        if (file.matches(".*huf")) {
            BinaryInputReader bir = new BinaryInputReader(file);
            String str = bir.readLines();
            
            Huffman huff = new Huffman();
            String dStr = huff.decompress(str);
            
            Writer wr = new Writer(file.substring(0, file.length() - 4));
            wr.write(dStr);
            System.out.println(file + " successfully decompressed into file " + file.substring(0, file.length() - 4));
        } else if (file.matches(".*lzw")) {
            BinaryInputReader bir = new BinaryInputReader(file);
            String str = bir.readLines();
            
            LZW lzw = new LZW();
            String dStr = lzw.decompress(str);
            
            Writer wr = new Writer(file.substring(0, file.length() - 4));
            wr.write(dStr);
            System.out.println(file + " successfully decompressed into file " + file.substring(0, file.length() - 4));
        } else {
            System.out.println("Unknown file format. (Give .huf or .lzw)");
        }
        
    }
    
    public static void compress() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose one: \n(1): Huffman \n(2): LZW");
        int option = Integer.valueOf(scanner.nextLine());
        System.out.println("Give filename: ");
        String file = scanner.nextLine();
        
        String str = "";
        try {
            InputReader reader = new InputReader(file);
            str = reader.readLines();
        } catch (Exception e) {
            System.out.println("File not found.");
            return;
        }
        
        if (option == 1) {
            Huffman huff = new Huffman();
            String cStr = huff.compress(str);
            
            BitWriter wr = new BitWriter(file + ".huf");
            wr.writeBits(cStr);
            System.out.println(file + " successfully compressed into file " + file + ".huf");
        } else if (option == 2) {
            LZW lzw = new LZW();
            String cStr = lzw.compress(str);
            BitWriter wr = new BitWriter(file + ".lzw");
            wr.writeBits(cStr);
            System.out.println(file + " successfully compressed into file " + file + ".lzw");
        }
    }
    
    public static void performance() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give file to run performance tests for:");
        String file = scanner.nextLine();
        
        String str = "";
        try {
            InputReader reader = new InputReader(file);
            str = reader.readLines();
        } catch (Exception e) {
            System.out.println("File not found.");
            return;
        }
        
        System.out.println("--------------------------");
        System.out.println("HUFFMAN");
        System.out.println("--------------------------");
        
        Huffman huff = new Huffman();
        
        long start = System.nanoTime();
        String cStr = huff.compress(str);
        long end = System.nanoTime();
        System.out.println("COMPRESS: " + (end - start)/1000000 + " ms");
        
        start = System.nanoTime();
        String dStr = huff.decompress(cStr);
        end = System.nanoTime();
        System.out.println("DECOMPRESS: " + (end - start)/1000000 + " ms");
        
        System.out.println("Size: " + str.length() + " compressed: " + cStr.length()/8);
        
        if (str.equals(dStr)) {
            System.out.println("MATCH!");
        } else {
            System.out.println("ERROR!");
            System.out.println("ORIGINAL: " + str.substring(0, 100));
            System.out.println("DECOMP: " + dStr.substring(0, 100));
        }
        
        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("Lempel-Ziv-Welch");
        System.out.println("--------------------------");
        
        LZW lzw = new LZW();
        start = System.nanoTime();
        cStr = lzw.compress(str);
        end = System.nanoTime();
        
        System.out.println("COMPRESS: " + (end - start)/1000000 + " ms");
        
        start = System.nanoTime();
        dStr = lzw.decompress(cStr);
        end = System.nanoTime();
        System.out.println("DECOMPRESS: " + (end - start)/1000000 + " ms");
        
        System.out.println("Size: " + str.length() + " compressed: " + cStr.length()/8);
        
        if (str.equals(dStr)) {
            System.out.println("MATCH!");
        } else {
            System.out.println("ERROR!");
            System.out.println("ORIGINAL: " + str.substring(0, 100));
            System.out.println("DECOMP: " + dStr.substring(0, 100));
        }
    }
    
}
