package implementations.Compiler;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
        if (args.length == 0) {
            throw new IllegalAccessException("引数にパスを指定してください");
        }
        if (!file.exists()) {
            throw new IOException("指定したパスは見つかりませんでした");
        }

        if (file.isDirectory()) {
            //
        } else {
            if (!file.getName().matches(".+\\.jack")) {
                throw new IllegalAccessException("jackファイルを指定してください");
            }
            JackTokenizer tokenizer = new JackTokenizer(file);

        }
    }
}
