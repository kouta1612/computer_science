package implementations.Compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CompilationEngine {
    private BufferedWriter bufferedWriter;
    private JackTokenizer tokenizer;

    CompilationEngine(JackTokenizer tokenizer, File xmlFile) throws Exception {
        if (xmlFile.isFile() && !xmlFile.getPath().endsWith(".xml")) {
            throw new IllegalAccessError("xmlファイルを指定してください。");
        }

        FileWriter fileWriter = new FileWriter(xmlFile);
        this.bufferedWriter = new BufferedWriter(fileWriter);
        this.tokenizer = tokenizer;

        writeCodes("<class>");
        compileClass();
        writeCodes("</class>");
    }

    public void compileClass() {

    }

    private void writeCodes(String... codes) throws IOException {
        for (String code : codes) {
            bufferedWriter.write(code);
            bufferedWriter.newLine();
        }
    }
}
