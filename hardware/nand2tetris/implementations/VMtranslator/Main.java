package implementations.VMtranslator;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Main {
    private static File input;
    private static Parser parser;
    private static CodeWriter codeWriter;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("引数にファイルまたはディレクトリのパスが指定されていません。");
        }

        input = new File(args[0]);
        codeWriter = new CodeWriter(input);

        if (input.isFile()) {
            parser = new Parser(input);
            writeCodesToAsmFile();
        }

        if (input.isDirectory()) {
            codeWriter.writeInit();
            FilenameFilter vmFileFilter = new vmFileFilter();
            File[] vmFiles = input.listFiles(vmFileFilter);
            for (File vmFile : vmFiles) {
                parser = new Parser(vmFile);
                writeCodesToAsmFile();
            }
        }

        codeWriter.close();
    }

    private static void writeCodesToAsmFile() throws IOException, Exception {
        codeWriter.setFileName(input.getName());
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
                case "C_FUNCTION":
                    codeWriter.writeFunction(parser.arg1(), parser.arg2());
                    break;
                case "C_CALL":
                    codeWriter.writeCall(parser.arg1(), parser.arg2());
                    break;
                case "C_RETURN":
                    codeWriter.writeReturn();
                    break;
                case "EMPTY":
                    continue;
                default:
                    throw new Exception("予期しないコマンドタイプでした。");
            }
        }
    }
}

class vmFileFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        if (name.endsWith(".vm")) {
            return true;
        }

        return false;
    }
}
