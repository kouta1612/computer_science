package implementations.Compiler;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class JackAnalyzer {
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
                System.out.println(jackFile.getName());
                JackTokenizer tokenizer = new JackTokenizer(jackFile);
            }
        } else {
            if (!file.getName().matches(".+\\.jack")) {
                throw new IllegalAccessException("jackファイルを指定してください");
            }

            JackTokenizer tokenizer = new JackTokenizer(file);
            while (tokenizer.hasMoreTokens()) {
                tokenizer.advance();
                switch (tokenizer.tokenType()) {
                    case "KEYWORD":
                        break;
                    case "SYMBOL":
                        break;
                    case "IDENTIFIER":
                        break;
                    case "INT_CONST":
                        break;
                    case "STRING_CONST":
                        break;
                    default:
                        throw new Exception("予期せぬトークンタイプが入力されました");
                }
            }
        }
    }
}
