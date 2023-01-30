package implementations.VMtranslator;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Main {
    private static CodeWriter codeWriter;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("引数にファイル名が指定されていません。");
        }

        File input = new File(args[0]);
        if (!input.exists()) {
            throw new IOException("指定したファイルまたはディレクトリが見つかりませんでした。");
        }

        if (input.isFile() && !input.getPath().endsWith(".vm")) {
            throw new IllegalAccessError("VMファイルを指定してください。");
        }

        String filename = getFileName(input);
        File asmFile = new File(filename);
        codeWriter = new CodeWriter(asmFile);

        if (input.isFile()) {
            Parser parser = new Parser(input);
            writeCodesToAsmFile(parser);
        }

        if (input.isDirectory()) {
            codeWriter.writeInit();
            FilenameFilter vmFileFilter = new vmFileFilter();
            File[] vmFiles = input.listFiles(vmFileFilter);
            for (File vmFile : vmFiles) {
                Parser parser = new Parser(vmFile);
                writeCodesToAsmFile(parser);
            }
        }

        codeWriter.close();
    }

    private static String getFileName(File input) {
        if (input.isFile()) {
            return input.getPath().replace(".vm", ".asm");
        }

        return input.getPath() + "/" + input.getName() + ".asm";
    }

    private static void writeCodesToAsmFile(Parser parser) throws IOException, Exception {
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
