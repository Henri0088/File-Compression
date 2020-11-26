
package compress;

import utils.CHashMap;
import static java.util.Objects.isNull;

/**
 * Class used to compress a UTF-8 coded String using Lempel-Ziv-Welch.
 * @author Henri Sundquist
 */
public class LZW {
    
    private CHashMap mapping;
    private String[] demapping;
    
    public LZW() {
        this.mapping = new CHashMap();
        this.demapping = new String[4096];
        
        for (int i = 0; i <= 255; i++) {
            this.mapping.put(Character.toString((char) i), i);
            this.demapping[i] = Character.toString((char) i);
        }
    }
    
    /**
     * Compresses a UTF-8 encoded string.
     * @param str UTF-8 encoded string.
     * @return compressed string containing only 1's and 0's
     */
    public String compress(String str) {
        str += "#";
        int i = 1;
        int lastW = 0;
        String compStr = "";
        while (i <= str.length()) {
            if (!mapping.containsKey(str.substring(lastW, i))) {
                compStr += getBinaryStr(mapping.get(str.substring(lastW, i - 1)));
                if (mapping.size() < 4096) {
                    mapping.put(str.substring(lastW, i), mapping.size());
                }
                lastW = i - 1;
            }
            i++;
        }
        return compStr;
    }
    
    /**
     * Decompresses a string compressed with an LZW algorithm.
     * Specifically one that uses UTF-8 as the starting dictionary and
     * encodes in fixed-length 12-bit codes.
     * @param str LZW-compressed string
     * @return Decompressed string
     */
    public String decompress(String str) {
        str += "#";
        int nextCode = 256;
        String dStr = "";
        
        String prevCode = str.substring(0, 12);
        dStr += demapping[binStrToInt(prevCode, 12)];
        
        int i = 12;
        while (str.length() > i + 12) {
            String currCode = str.substring(i, i + 12);
            String s = demapping[binStrToInt(currCode, 12)];
            
            if (isNull(s)) {
                String prev = demapping[binStrToInt(prevCode, 12)];
                s = prev + Character.toString(prev.charAt(0));
            }
            
            dStr += s;
            String chr = Character.toString(s.charAt(0));
            
            if (nextCode < 4096) {
                demapping[nextCode] = demapping[binStrToInt(prevCode, 12)] + chr;
                nextCode++;
            }
            prevCode = currCode;
            i = i + 12;
        }
        
        return dStr;
    }
    
    private int binStrToInt(String str, int codeLength) {
        int res = 0; 
        int val = 2048;
        
        for (int i = 0; i <= codeLength - 1; i++) {
            if (str.charAt(i) == '1') {
                res += val;
            }
            val = val / 2;
        }
        return res;
    }
    
    private String getBinaryStr(int n) {
        String bin = "";
        int bits = 12;
        for (int i = bits - 1; i >= 0; i--) {
            if (Math.pow(2, i) <= n) {
                bin += "1";
                n -= Math.pow(2, i);
            } else {
                bin += "0";
            }
        }
        return bin;
    }
}
