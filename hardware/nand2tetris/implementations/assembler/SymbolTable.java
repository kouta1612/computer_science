package implementations.assembler;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private static Map<String, Integer> symbolTable;

    SymbolTable() {
        symbolTable = new HashMap<>() {
            {
                put("SP", 0);
                put("LCL", 1);
                put("ARG", 2);
                put("THIS", 3);
                put("THAT", 4);
                put("R0", 0);
                put("R1", 1);
                put("R2", 2);
                put("R3", 3);
                put("R4", 4);
                put("R5", 5);
                put("R6", 6);
                put("R7", 7);
                put("R8", 8);
                put("R9", 9);
                put("R10", 10);
                put("R11", 11);
                put("R12", 12);
                put("R13", 13);
                put("R14", 14);
                put("R15", 15);
                put("SCREEN", 16384);
                put("KBD", 24576);
            }
        };
    }

    public boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    public void addEntry(String symbol, Integer address) {
        symbolTable.put(symbol, address);
    }

    public Integer getAddress(String symbol) {
        return symbolTable.get(symbol);
    }
}
