package implementations.assembler;

import java.util.HashMap;
import java.util.Map;

public class Code {
    static final Map<String, String> COMP_MAP = new HashMap<>() {
        {
            put("0", "0101010");
            put("1", "0111111");
            put("-1", "0111010");
            put("D", "0001100");
            put("A", "0110000");
            put("!D", "0001100");
            put("!A", "0110001");
            put("-D", "0001111");
            put("-A", "0110001");
            put("D+1", "0011111");
            put("A+1", "0110111");
            put("D-1", "0001110");
            put("A-1", "0110010");
            put("D+A", "0000010");
            put("D-A", "0010011");
            put("A-D", "0000111");
            put("D&A", "0000000");
            put("D|A", "0010101");
            put("M", "1110000");
            put("!M", "1110001");
            put("-M", "1110001");
            put("M+1", "1110111");
            put("M-1", "1110010");
            put("D+M", "1000010");
            put("D-M", "1010011");
            put("M-D", "1000111");
            put("D&M", "1000000");
            put("D|M", "1010101");
        }
    };

    static final Map<String, String> DEST_MAP = new HashMap<>() {
        {
            put("null", "000");
            put("M", "001");
            put("D", "010");
            put("MD", "011");
            put("A", "100");
            put("AM", "101");
            put("AD", "110");
            put("AMD", "111");
        }
    };

    static final Map<String, String> JUMP_MAP = new HashMap<>() {
        {
            put("null", "000");
            put("JGT", "001");
            put("JEQ", "010");
            put("JGE", "011");
            put("JLT", "100");
            put("JNE", "101");
            put("JLE", "110");
            put("JMP", "111");
        }
    };

    public String comp(String mnemonic) {
        if (mnemonic == null) {
            return COMP_MAP.get("0");
        }

        return COMP_MAP.get(mnemonic);
    }

    public String dest(String mnemonic) {
        if (mnemonic == null) {
            return DEST_MAP.get("null");
        }

        return DEST_MAP.get(mnemonic);
    }

    public String jump(String mnemonic) {
        if (mnemonic == null) {
            return JUMP_MAP.get("null");
        }

        return JUMP_MAP.get(mnemonic);
    }
}
