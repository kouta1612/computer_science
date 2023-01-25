package implementations.VMtranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private BufferedReader bufferedReader;
    public String currentCommand;
    private String currentCommandType;

    private static final Pattern ARITHMETIC_PATTERN = Pattern.compile("^(add|sub|neg|eq|gt|lt|and|or|not)$");
    private static final Pattern PUSH_PATTERN = Pattern
            .compile("^push(argument|local|static|constant|this|that|pointer|temp)(\\d+)$");
    private static final Pattern POP_PATTERN = Pattern
            .compile("^pop(argument|local|static|constant|this|that|pointer|temp)(\\d+)$");

    Parser(File file) throws FileNotFoundException {
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
        if (currentCommand.equals("")) {
            currentCommandType = "EMPTY";
            return currentCommandType;
        }
        if (ARITHMETIC_PATTERN.matcher(currentCommand).matches()) {
            currentCommandType = "C_ARITHMETIC";
            return currentCommandType;
        }
        if (PUSH_PATTERN.matcher(currentCommand).matches()) {
            currentCommandType = "C_PUSH";
            return currentCommandType;
        }
        if (POP_PATTERN.matcher(currentCommand).matches()) {
            currentCommandType = "C_POP";
            return currentCommandType;
        }

        throw new Exception("予期しないコマンドタイプが設定されています。");
    }

    public String arg1() throws Exception {
        switch (currentCommandType) {
            case "C_ARITHMETIC":
                Matcher matcher = ARITHMETIC_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("算術コマンドでパースエラーが発生しました。");
                }
                return matcher.group(1);
            case "C_PUSH":
                matcher = PUSH_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("PUSHコマンドでパースエラーが発生しました。");
                }
                return matcher.group(1);
            case "C_POP":
                matcher = POP_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("POPコマンドでパースエラーが発生しました。");
                }
                return matcher.group(1);
            case "C_RETURN":
                throw new IllegalArgumentException("C_RETURN は引数を持ちません。");
            default:
                throw new Exception("予期しない引数です。");
        }
    }

    public int arg2() throws Exception {
        switch (currentCommandType) {
            case "C_PUSH":
                Matcher matcher = PUSH_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("PUSHコマンドでパースエラーが発生しました。");
                }
                return Integer.valueOf(matcher.group(2));
            case "C_POP":
                matcher = POP_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("POPコマンドでパースエラーが発生しました。");
                }
                return Integer.valueOf(matcher.group(2));
            default:
                throw new Exception("引数を持たないか予期しないコマンドです。");
        }
    }
}
