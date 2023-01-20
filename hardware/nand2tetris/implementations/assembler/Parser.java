package implementations.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Parser {
    private BufferedReader reader;
    private String currentCommand;

    private static final Pattern A_COMMAND_PATTERN = Pattern.compile("@([a-zA-Z0-9_.$:]+)");
    private static final Pattern L_COMMAND_PATTERN = Pattern.compile("\\(([a-zA-Z0-9_.$:]+)\\)");
    private static final Pattern C_COMMAND_PATTERN = Pattern.compile("(?:(M?D?A?)=)?([^;]+)(?:;(.+))?");

    Parser(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException("ファイルが存在しませんでした");
        }
        FileReader fileReader = new FileReader(file);
        reader = new BufferedReader(fileReader);
        currentCommand = null;
    }

    public void advance() throws IOException {
        try {
            currentCommand = reader.readLine();
            if (currentCommand == null) {
                return;
            }

            currentCommand = currentCommand.replaceAll(" ", "");
            currentCommand = currentCommand.replaceAll("//.*$", "");
        } catch (IOException io) {
            throw new IOException("読み取り時にエラーが発生しました。");
        }
    }

    public String getCommand() {
        return currentCommand;
    }

    public String commandType() {
        if (currentCommand.indexOf("@") == 0) {
            return "A_COMMAND";
        } else if (currentCommand.indexOf("(") == 0) {
            return "L_COMMAND";
        } else {
            return "C_COMMAND";
        }
    }

    public String symbol() throws Exception {
        switch (commandType()) {
            case "A_COMMAND":
                Matcher aMatcher = A_COMMAND_PATTERN.matcher(currentCommand);
                if (!aMatcher.matches()) {
                    throw new Exception("Aコマンドでパースエラーが発生しました。");
                }

                return aMatcher.group(1);
            case "L_COMMAND":
                Matcher lMatcher = L_COMMAND_PATTERN.matcher(currentCommand);
                if (!lMatcher.matches()) {
                    throw new Exception("Lコマンドでパースエラーが発生しました。");
                }

                return lMatcher.group(1);
            default:
                throw new Exception("現在のコマンドはC_COMMANDなのでシンボルを持ちません。");
        }
    }

    public String dest() throws Exception {
        switch (commandType()) {
            case "C_COMMAND":
                Matcher matcher = C_COMMAND_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    return null;
                }

                return matcher.group(1);
            default:
                throw new Exception("C_COMMAND以外は受け付けていません。");
        }
    }

    public String comp() throws Exception {
        switch (commandType()) {
            case "C_COMMAND":
                Matcher matcher = C_COMMAND_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    return null;
                }

                return matcher.group(2);
            default:
                throw new Exception("C_COMMAND以外は受け付けていません。");
        }
    }

    public String jump() throws Exception {
        switch (commandType()) {
            case "C_COMMAND":
                Matcher matcher = C_COMMAND_PATTERN.matcher(currentCommand);
                if (!matcher.matches()) {
                    return null;
                }

                return matcher.group(3);
            default:
                throw new Exception("C_COMMAND以外は受け付けていません。");
        }
    }
}
