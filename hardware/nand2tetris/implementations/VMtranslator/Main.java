package implementations.VMtranslator;

import java.io.File;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern VM_FILENAME_PATTERN = Pattern.compile(".+(\\.vm)$");

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("引数にファイル名が指定されていません。");
        }
        if (!VM_FILENAME_PATTERN.matcher(args[0]).matches()) {
            throw new IllegalArgumentException("VMファイルではありません。");
        }

        File inFile = new File(args[0]);
        Parser parser = new Parser(inFile);

        File outFile = new File(args[0].replace(".vm", ".asm"));
        CodeWriter codeWriter = new CodeWriter(outFile);
        while (parser.advance()) {
            switch (parser.commandType()) {
                case "C_ARITHMETIC":
                    codeWriter.writeArithmetic(parser.arg1());
                    break;
                case "C_PUSH":
                    codeWriter.writePushPop("C_PUSH", parser.arg1(), parser.arg2());
                    break;
                case "C_POP":
                    codeWriter.writePushPop("C_POP", parser.arg1(), parser.arg2());
                    break;
                case "C_LABEL":
                    codeWriter.writeLabel(parser.arg1());
                    break;
                case "C_IF":
                    codeWriter.writeIf(parser.arg1());
                    break;
                case "C_GOTO":
                    codeWriter.writeGoto(parser.arg1());
                    break;
                case "EMPTY":
                    continue;
                default:
                    throw new Exception("予期しないコマンドタイプでした。");
            }
        }

        codeWriter.close();
    }
}
