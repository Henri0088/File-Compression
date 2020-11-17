
package compress;

import java.util.HashMap;
import java.lang.Math;
import java.util.Map;

public class LZW {
    
    private HashMap<String, Integer> mapping;
    private String[] demapping;
    
    public LZW() {
        this.mapping = new HashMap();
        this.demapping = new String[4096];
        
        for (int i = 0; i <= 255; i++) {
            this.mapping.put(Character.toString((char) i), i);
            this.demapping[i] = Character.toString((char) i);
        }
    }
    
    public HashMap getMap() {
        return this.mapping;
    }
    
    public String compress(String str) {
        str += "#";
        int i = 1;
        int lastW = 0;
        String compStr = "";
        while (i <= str.length()) {
            
            if (!mapping.keySet().contains(str.substring(lastW, i))) {
                compStr += getBinaryStr(mapping.get(str.substring(lastW, i - 1)));
                mapping.put(str.substring(lastW, i), mapping.size());
                lastW = i - 1;
            }
            i++;
        }
        return compStr;
    }
    
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
            dStr += s;
            
            String chr = Character.toString(s.charAt(0));
            demapping[nextCode] = demapping[binStrToInt(prevCode, 12)] + chr;
            nextCode++;
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
            if (Math.pow(2,i) <= n) {
                bin += "1";
                n -= Math.pow(2, i);
            } else {
                bin += "0";
            }
        }
        return bin;
    }
    
}
