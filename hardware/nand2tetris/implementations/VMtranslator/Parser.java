package implementations.VMtranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Parser {
    private BufferedReader bufferedReader;
    private String currentCommand;

    private static final Pattern ARITHMETIC_PATTERN = Pattern.compile("^(add|sub|neg|eq|gt|lt|and|or|not)$");
    private static final Pattern PUSH_PATTERN = Pattern
            .compile("^push(argument|local|static|constant|this|that|pointer|temp)(\\d)");
    private static final Pattern POP_PATTERN = Pattern
            .compile("^pop(argument|local|static|constant|this|that|pointer|temp)(\\d)");

    Parser(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        FileReader fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
    }

    public boolean advance() throws IOException {
        try {
            currentCommand = bufferedReader.readLine();
            if (currentCommand == null) {
                bufferedReader.close();
                return false;
            }

            currentCommand = currentCommand.replaceAll(" ", "");
            currentCommand = currentCommand.replaceAll("//.*$", "");

            return true;
        } catch (Exception e) {
            bufferedReader.close();
            throw new IOException("読み取り時にエラーが発生しました。");
        }
    }

    public String commandType() throws Exception {
        if (ARITHMETIC_PATTERN.matcher(currentCommand).matches()) {
            return "C_ARITHMETIC";
        }
        if (PUSH_PATTERN.matcher(currentCommand).matches()) {

            return "C_PUSH";
        }
        if (POP_PATTERN.matcher(currentCommand).matches()) {
            return "C_POP";
        }

        throw new Exception("予期しないコマンドタイプが設定されています。");
    }

    public String arg1() throws Exception {
        switch (commandType()) {
            case "C_ARITHMETIC":
                return currentCommand;
            case "C_PUSH":
                return PUSH_PATTERN.matcher(currentCommand).group(1);
            case "C_POP":
                return POP_PATTERN.matcher(currentCommand).group(1);
            case "C_RETURN":
                throw new IllegalArgumentException("C_RETURN は引数を持ちません。");
            default:
                throw new Exception("予期しない引数です。");
        }
    }

    public String arg2() throws Exception {
        switch (commandType()) {
            case "C_PUSH":
                return PUSH_PATTERN.matcher(currentCommand).group(2);
            case "C_POP":
                return PUSH_PATTERN.matcher(currentCommand).group(2);
            default:
                throw new Exception("引数を持たないか予期しないコマンドです。");
        }
    }
}
