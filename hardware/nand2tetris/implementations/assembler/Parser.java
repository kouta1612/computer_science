package implementations.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Parser {
    BufferedReader reader;
    String currentCommand;

    Parser(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException("file not exists.");
        }
        FileReader fileReader = new FileReader(file);
        reader = new BufferedReader(fileReader);
        currentCommand = null;
    }
}