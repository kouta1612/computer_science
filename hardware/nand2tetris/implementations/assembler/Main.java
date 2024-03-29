package implementations.assembler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("^[^0-9].*");
    private static int addressNumber = 16;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalAccessException("第一引数にファイルパスが設定されていません。");
        }

        Parser parser = new Parser(args[0]);
        SymbolTable symbolTable = new SymbolTable();

        // 1回目のパス（シンボルテーブルに記録）
        int rowNum = 0;
        while (parser.advance()) {
            switch (parser.commandType()) {
                case "L_COMMAND":
                    symbolTable.addEntry(parser.symbol(), rowNum);
                    break;
                case "C_COMMAND":
                case "A_COMMAND":
                    rowNum++;
                    break;
                default:
                    break;
            }
        }

        // 2回目のパス（アセンブルしてファイルに書き出し）
        parser = new Parser(args[0]);
        String fileName = args[0].replace("asm", "hack");
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
            while (true) {
                if (!parser.advance()) {
                    break;
                }

                String commandType = parser.commandType();
                if (commandType.equals("A_COMMAND")) {
                    String symbol = parser.symbol();
                    String addressBinary = getAddressBinary(symbolTable, symbol);
                    writer.write(addressBinary);
                    writer.newLine();
                } else if (commandType.equals("C_COMMAND")) {
                    String binary = buildInstructionBinary(parser);
                    writer.write(binary);
                    writer.newLine();
                } else if (commandType.equals("EMPTY") || commandType.equals("L_COMMAND")) {
                    continue;
                } else {
                    throw new Exception("予期しないエラーが発生しました。");
                }
            }

            writer.flush();
            writer.close();
        }
    }

    private static String getAddressBinary(SymbolTable symbolTable, String symbol) {
        String result = null;
        if (SYMBOL_PATTERN.matcher(symbol).matches()) {
            if (symbolTable.contains(symbol)) {
                symbol = String.valueOf(symbolTable.getAddress(symbol));
                result = buildAddressBinary(symbol);
            } else {
                symbolTable.addEntry(symbol, addressNumber);
                result = buildAddressBinary(String.valueOf(addressNumber));
                addressNumber++;
            }
        } else {
            result = buildAddressBinary(symbol);
        }

        return result;
    }

    private static String buildInstructionBinary(Parser parser) throws Exception {
        StringBuilder builder = new StringBuilder("111");
        Code code = new Code();
        builder.append(code.comp(parser.comp()));
        builder.append(code.dest(parser.dest()));
        builder.append(code.jump(parser.jump()));

        return builder.toString();
    }

    private static String buildAddressBinary(String numString) {
        return to16BitBinaryString(numString);
    }

    private static String to16BitBinaryString(String numString) {
        int n = Integer.valueOf(numString);
        String binary = Integer.toBinaryString(n);
        StringBuilder builder = new StringBuilder(binary);
        while (builder.length() < 16) {
            builder.insert(0, "0");
        }

        return builder.toString();
    }
}
