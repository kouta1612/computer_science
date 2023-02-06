package implementations.Compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JackTokenizer {
    private static final String WORD_PATTERN = "[a-zA-Z0-9]";
    private static final String SYMBOL_PATTERN = "[{}()\\[\\].,;+\\-*/&|<>=~]";
    private static final Map<Character, String> SYMBOL_REPLACE_MAP = new HashMap<>() {
        {
            put('<', "&lt;");
            put('>', "&gt;");
            put('&', "&amp;");
        }
    };

    private static BufferedReader bufferedReader;
    private static List<String> tokens;

    JackTokenizer(File file) throws Exception {
        FileReader fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
        addTokens();
    }

    public void advance() {

    }

    private void addTokens() throws Exception {
        while (bufferedReader.ready()) {
            String readLine = bufferedReader.readLine();
            readLine = readLine.replaceAll("//.*$", "");
            readLine = readLine.replaceAll("/\\*\\*.*\\*/$", "");
            readLine = readLine.trim();
            if (readLine.equals("")) {
                continue;
            }
            // System.out.println(readLine);
            List<String> tests = getTokens(readLine);
            for (String test : tests) {
                System.out.println(test);
            }
        }
        bufferedReader.close();
    }

    private List<String> getTokens(String line) throws Exception {
        List<String> result = new ArrayList<>();
        char[] cs = line.toCharArray();
        StringBuilder tokenBuilder = new StringBuilder();

        for (int i = 0; i < cs.length; i++) {
            if (Character.toString(cs[i]).matches(WORD_PATTERN)) {
                tokenBuilder.append(cs[i]);
            } else if (Character.toString(cs[i]).equals(" ")) {
                if (!tokenBuilder.toString().equals("")) {
                    result.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                }
            } else if (Character.toString(cs[i]).matches(SYMBOL_PATTERN)) {
                if (cs[i] == '<' || cs[i] == '>' || cs[i] == '&') {
                    tokenBuilder.append(SYMBOL_REPLACE_MAP.get(cs[i]));
                } else if (cs[i] == ';' || cs[i] == '{' || cs[i] == '}' || cs[i] == '(' || cs[i] == ')'
                        || cs[i] == ',') {
                    if (!tokenBuilder.toString().equals("")) {
                        result.add(tokenBuilder.toString());
                    }
                    result.add(Character.toString(cs[i]));
                    tokenBuilder.setLength(0);
                } else {
                    tokenBuilder.append(cs[i]);
                }
            } else if (i == cs.length - 1 && !tokenBuilder.toString().equals("")) {
                result.add(tokenBuilder.toString());
                tokenBuilder.setLength(0);
            } else if (cs[i] == '"') {
                i++;
                while (cs[i] != '"') {
                    tokenBuilder.append(cs[i]);
                    i++;
                }
                result.add(tokenBuilder.toString());
                tokenBuilder.setLength(0);
            } else {
                // throw new Exception("kota error.");
            }
        }

        return result;
    }
}
