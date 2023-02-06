package implementations.Compiler;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
        JackTokenizer tokenizer = new JackTokenizer(file);
    }
}
