package implementations.Compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

    private static final Map<String, String> SYMBOL_REPLACE_MAP = new HashMap<>() {
        {
            put("<", "&lt;");
            put(">", "&gt;");
            put("&", "&amp;");
        }
    };

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private List<String> tokens = new ArrayList<>();
    private int tokenIndex = 0;
    private int intVal;
    private String tokenType, keyword, symbol, identifier, stringVal;

    JackTokenizer(File file) throws Exception {
        if (!file.exists()) {
            throw new FileNotFoundException("指定したファイルまたはディレクトリが見つかりませんでした。");
        }
        if (file.isFile() && !file.getPath().endsWith(".jack")) {
            throw new IllegalAccessError("jackファイルを指定してください。");
        }

        tokenizing(file);
        generateXmlFile(file);
    }

    public boolean hasMoreTokens() {
        return tokens.size() > tokenIndex;
    }

    public void advance() {
        String token = tokens.get(tokenIndex);
        if (token.matches(KEYWORD_PATTERN)) {
            tokenType = "KEYWORD";
            keyword = token.toUpperCase();
        } else if (token.matches(SYMBOL_PATTERN)) {
            token = SYMBOL_REPLACE_MAP.containsKey(token) ? SYMBOL_REPLACE_MAP.get(token) : token;
            tokenType = "SYMBOL";
            symbol = token;
        } else if (token.matches(IDENTIFIER_PATTERN)) {
            tokenType = "IDENTIFIER";
            identifier = token;
        } else if (token.matches(INT_PATTERN)) {
            if (!(0 <= Integer.parseInt(token) && Integer.parseInt(token) <= 32767)) {
                throw new IllegalArgumentException("integerConstantの値は0から32767までの数字です");
            }
            tokenType = "INT_CONST";
            intVal = Integer.parseInt(token);
        } else if (token.matches(STRING_PATTERN)) {
            token = token.length() > 2 ? token.substring(1, token.length() - 1) : "";
            tokenType = "STRING_CONST";
            stringVal = token;
        }

        tokenIndex++;
    }

    public String tokenType() {
        return tokenType;
    }

    public String keyword() {
        return keyword;
    }

    public String symbol() {
        return symbol;
    }

    public String identifier() {
        return identifier;
    }

    public int intVal() {
        return intVal;
    }

    public String StringVal() {
        return stringVal;
    }

    private void generateXmlFile(File file) throws Exception {
        String path = getOutFilePath(file);
        File outFile = new File(path);
        FileWriter fileWriter = new FileWriter(outFile);
        bufferedWriter = new BufferedWriter(fileWriter);
        writeCodes("<tokens>");
        for (String token : tokens) {
            if (token.matches(KEYWORD_PATTERN)) {
                writeCodes("<keyword> " + token + " </keyword>");
            } else if (token.matches(SYMBOL_PATTERN)) {
                token = SYMBOL_REPLACE_MAP.containsKey(token) ? SYMBOL_REPLACE_MAP.get(token) : token;
                writeCodes("<symbol> " + token + " </symbol>");
            } else if (token.matches(IDENTIFIER_PATTERN)) {
                writeCodes("<identifier> " + token + " </identifier>");
            } else if (token.matches(INT_PATTERN)) {
                if (!(0 <= Integer.parseInt(token) && Integer.parseInt(token) <= 32767)) {
                    throw new IllegalArgumentException("integerConstantの値は0から32767までの数字です");
                }
                writeCodes("<integerConstant> " + token + " </integerConstant>");
            } else if (token.matches(STRING_PATTERN)) {
                token = token.length() > 2 ? token.substring(1, token.length() - 1) : "";
                writeCodes("<stringConstant> " + token + " </stringConstant>");
            } else {
                bufferedWriter.close();
                throw new Exception("予期せぬトークンが入力されました");
            }
        }
        writeCodes("</tokens>");

        bufferedWriter.close();
    }

    private void writeCodes(String... codes) throws IOException {
        for (String code : codes) {
            bufferedWriter.write(code);
            bufferedWriter.newLine();
        }
    }

    private String getOutFilePath(File file) {
        return file.getPath().replace(".jack", "T.xml");
    }

    private void tokenizing(File file) throws Exception {
        FileReader fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);

        while (bufferedReader.ready()) {
            String readLine = bufferedReader.readLine();
            // 1行で完結するコメントを事前に削除
            readLine = readLine.replaceAll("//.*$", "");
            readLine = readLine.replaceAll("/\\*.*\\*/$", "");
            readLine = readLine.replaceAll("/\\*\\*.*\\*/$", "");
            readLine = readLine.trim();
            // 複数行から構成されるコメントをスキップ
            if (readLine.matches("/\\*+")) {
                String commentLine;
                while (true) {
                    commentLine = bufferedReader.readLine().trim();
                    if (commentLine.matches("\\*+/")) {
                        break;
                    }
                }
                readLine = commentLine.replaceAll("\\*+/", "").trim();
            }
            // 空行をスキップ
            if (readLine.equals("")) {
                continue;
            }

            setTokens(readLine);
        }
        bufferedReader.close();
    }

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
