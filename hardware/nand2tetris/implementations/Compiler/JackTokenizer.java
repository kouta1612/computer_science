package implementations.Compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JackTokenizer {
    private static final String WORD_PATTERN = "^[a-zA-Z0-9]$";
    private static final String SYMBOL_PATTERN = "^[{}()\\[\\].,;+\\-*/&|<>=~]$";
    private static final String KEYWORD_PATTERN = "^class|constructor|function|method|field|static|var|int|char|boolean|void|true|false|null|this|let|do|if|else|while|return$";
    private static final String INT_PATTERN = "^\\d+$";
    private static final String STRING_PATTERN = "^\"[^\"\n]*\"$";
    private static final String IDENTIFIER_PATTERN = "^[^\\d][a-zA-z0-9_]*$";

    private static final Map<Character, String> SYMBOL_REPLACE_MAP = new HashMap<>() {
        {
            put('<', "&lt;");
            put('>', "&gt;");
            put('&', "&amp;");
        }
    };

    private static BufferedReader bufferedReader;
    private static List<String> tokens;
    private static int tokenIndex = 0;
    private static String currentToken;
    private static String currentTokenType;
    // private static List<String> xmlTokens;

    JackTokenizer(File file) throws Exception {
        FileReader fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
        addTokens();
    }

    public boolean hasMoreTokens() {
        return tokens.size() > tokenIndex;
    }

    public void advance() {
        String token = tokens.get(tokenIndex);
        if (token.equals(KEYWORD_PATTERN)) {
            currentToken = "<keyword> " + token + " </keyword>";
            currentTokenType = "KEYWORD";
        } else if (token.equals(SYMBOL_PATTERN)) {
            currentToken = "<symbol> " + token + " </symbol>";
            currentTokenType = "SYMBOL";
        } else if (token.equals(IDENTIFIER_PATTERN)) {
            currentToken = "<identifier> " + token + " </identifier>";
            currentTokenType = "IDENTIFIER";
        } else if (token.equals(INT_PATTERN)) {
            if (!(0 <= Integer.valueOf(token) && Integer.valueOf(token) <= 32767)) {
                throw new IllegalArgumentException("integerConstantの値は0から32767までの数字です");
            }
            currentToken = "<integerConstant> " + token + "\n</integerConstant>";
            currentTokenType = "INT_CONST";
        } else if (token.equals(STRING_PATTERN)) {
            if (token.length() > 2) {
                token = token.substring(1, token.length() - 2);
            } else {
                token = "";
            }
            currentToken = "<stringConstant> " + token + "\n</stringConstant>";
            currentTokenType = "STRING_CONST";
        }
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
            setTokens(readLine);
        }
        bufferedReader.close();
    }

    // private void lexicalAnalyze() throws Exception {
    // for (String token : tokens) {

    // }
    // }

    private void setTokens(String line) throws Exception {
        char[] cs = line.toCharArray();
        StringBuilder tokenBuilder = new StringBuilder();

        for (int i = 0; i < cs.length; i++) {
            if (Character.toString(cs[i]).matches(WORD_PATTERN)) {
                tokenBuilder.append(cs[i]);
            } else if (Character.toString(cs[i]).equals(" ")) {
                if (!tokenBuilder.toString().equals("")) {
                    tokens.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                }
            } else if (Character.toString(cs[i]).matches(SYMBOL_PATTERN)) {
                if (!tokenBuilder.toString().equals("")) {
                    tokens.add(tokenBuilder.toString());
                }
                tokens.add(Character.toString(cs[i]));
                tokenBuilder.setLength(0);
            } else if (i == cs.length - 1 && !tokenBuilder.toString().equals("")) {
                tokens.add(tokenBuilder.toString());
                tokenBuilder.setLength(0);
            } else if (cs[i] == '"') {
                tokenBuilder.append(cs[i]);
                i++;
                while (cs[i] != '"') {
                    tokenBuilder.append(cs[i]);
                    i++;
                }
                tokenBuilder.append(cs[i]);
                tokens.add(tokenBuilder.toString());
                tokenBuilder.setLength(0);
            } else {
                throw new Exception("トークン取得時に予期せぬエラーが発生しました。");
            }
        }
    }
}
