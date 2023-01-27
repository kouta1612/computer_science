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
            .compile("^push(?:\\s*)(argument|local|static|constant|this|that|pointer|temp)(?:\\s*)(\\d+)$");
    private static final Pattern POP_PATTERN = Pattern
            .compile("^pop(?:\\s*)(argument|local|static|constant|this|that|pointer|temp)(?:\\s*)(\\d+)$");
    private static final Pattern LABEL_PATTERN = Pattern
            .compile("^label(?:\\s*)([^\\d][a-zA-Z\\d_.:]*)$");
    private static final Pattern GOTO_PATTERN = Pattern
            .compile("^goto(?:\\s*)([^\\d][a-zA-Z\\d_.:]*)$");
    private static final Pattern IF_PATTERN = Pattern
            .compile("^if-goto(?:\\s*)([^\\d][a-zA-Z\\d_.:]*)$");
    private static final Pattern FUNCTION_PATTERN = Pattern
            .compile("^function(?:\\s*)([^\\d][a-zA-Z\\d_.:]*)(?:\\s*)(\\d+)$");
    private static final Pattern CALL_PATTERN = Pattern
            .compile("^call(?:\\s*)([^\\d][a-zA-Z\\d_.:]*)(?:\\s*)(\\d+)$");
    private static final Pattern RETURN_PATTERN = Pattern
            .compile("^(return)$");

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

            currentCommand = currentCommand.replaceAll("//.*$", "");
            currentCommand = currentCommand.trim();

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
        if (LABEL_PATTERN.matcher(currentCommand).matches()) {
            currentCommandType = "C_LABEL";
            return currentCommandType;
        }
        if (GOTO_PATTERN.matcher(currentCommand).matches()) {
            currentCommandType = "C_GOTO";
            return currentCommandType;
        }
        if (IF_PATTERN.matcher(currentCommand).matches()) {
            currentCommandType = "C_IF";
            return currentCommandType;
        }
        if (FUNCTION_PATTERN.matcher(currentCommand).matches()) {
            currentCommandType = "C_FUNCTION";
            return currentCommandType;
        }
        if (CALL_PATTERN.matcher(currentCommand).matches()) {
            currentCommandType = "C_CALL";
            return currentCommandType;
        }
        if (RETURN_PATTERN.matcher(currentCommand).matches()) {
            currentCommandType = "C_RETURN";
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
            case "C_LABEL":
                matcher = LABEL_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("LABELコマンドでパースエラーが発生しました。");
                }
                return matcher.group(1);
            case "C_GOTO":
                matcher = GOTO_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("GOTOコマンドでパースエラーが発生しました。");
                }
                return matcher.group(1);
            case "C_IF":
                matcher = IF_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("IFコマンドでパースエラーが発生しました。");
                }
                return matcher.group(1);
            case "C_FUNCTION":
                matcher = FUNCTION_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("FUNCTIONコマンドでパースエラーが発生しました。");
                }
                return matcher.group(1);
            case "C_CALL":
                matcher = CALL_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("CALLコマンドでパースエラーが発生しました。");
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
            case "C_FUNCTION":
                matcher = FUNCTION_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("FUNCTIONコマンドでパースエラーが発生しました。");
                }
                return Integer.valueOf(matcher.group(2));
            case "C_CALL":
                matcher = CALL_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    throw new Exception("CALLコマンドでパースエラーが発生しました。");
                }
                return Integer.valueOf(matcher.group(2));
            default:
                throw new Exception("引数を持たないか予期しないコマンドです。");
        }
    }
}
