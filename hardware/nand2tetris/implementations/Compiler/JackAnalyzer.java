package implementations.Compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

public class JackAnalyzer {
    private static BufferedWriter bufferedWriter;

    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
        if (args.length == 0) {
            throw new IllegalAccessException("引数にパスを指定してください");
        }
        if (!file.exists()) {
            throw new IOException("指定したパスは見つかりませんでした");
        }

        if (file.isDirectory()) {
            FilenameFilter vmFileFilter = new JackFileFilter();
            File[] jackFiles = file.listFiles(vmFileFilter);
            for (File jackFile : jackFiles) {
                String path = getOutFilePath(jackFile);
                File outFile = new File(path);

                JackTokenizer tokenizer = new JackTokenizer(jackFile);
                CompilationEngine compilationEngine = new CompilationEngine(tokenizer, outFile);
            }
        } else {
            if (!file.getName().matches(".+\\.jack")) {
                throw new IllegalAccessException("jackファイルを指定してください");
            }

            String path = getOutFilePath(file);
            File outFile = new File(path);

            JackTokenizer tokenizer = new JackTokenizer(file);
            CompilationEngine compilationEngine = new CompilationEngine(tokenizer, outFile);
        }
    }

    private static String getOutFilePath(File file) {
        return file.getPath().replace(".jack", ".xml");
    }
}
