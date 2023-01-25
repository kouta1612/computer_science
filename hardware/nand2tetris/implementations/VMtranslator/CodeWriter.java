package implementations.VMtranslator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class CodeWriter {
    private static BufferedWriter bufferedWriter;
    private static int labelNumber = 0;

    private static final Pattern NEGA_COMMAND_PATTERN = Pattern.compile("^(neg|not)$");
    private static final Pattern CALC_COMMAND_PATTERN = Pattern.compile("^(add|sub|and|or)$");
    private static final Pattern BOOL_COMMAND_PATTERN = Pattern.compile("^(eq|gt|lt)$");

    CodeWriter(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        bufferedWriter = new BufferedWriter(fileWriter);
    }

    public void writeArithmetic(String command) throws IOException {
        if (NEGA_COMMAND_PATTERN.matcher(command).matches()) {
            // スタックポインタを更新
            decrementSP();
            // 演算を実行
            codeWrites("A=M");
            if (command.equals("neg")) {
                codeWrites("M=-M");
            }
            if (command.equals("not")) {
                codeWrites("M=!M");
            }
        }
        if (CALC_COMMAND_PATTERN.matcher(command).matches()) {
            // スタックポインタを更新
            decrementSP();
            // Dレジスタにスタックを代入
            codeWrites("A=M", "D=M");
            // スタックポインタを更新
            decrementSP();

            // 演算を実行
            codeWrites("A=M");
            if (command.equals("add")) {
                codeWrites("M=M+D");
            }
            if (command.equals("sub")) {
                codeWrites("M=M-D");
            }
            if (command.equals("and")) {
                codeWrites("M=M&D");
            }
            if (command.equals("or")) {
                codeWrites("M=M|D");
            }
        }

        if (BOOL_COMMAND_PATTERN.matcher(command).matches()) {
            // スタックポインタを更新
            decrementSP();
            // Dレジスタにスタックを代入
            codeWrites("A=M", "D=M");
            // スタックポインタを更新
            decrementSP();

            // 演算を実行
            codeWrites("A=M", "D=M-D");
            String trueLabel = getLabel();
            String falseLabel = getLabel();
            // 条件によって遷移先に移動
            codeWrites("@" + trueLabel);
            if (command.equals("eq")) {
                codeWrites("D;JEQ");
            }
            if (command.equals("lt")) {
                codeWrites("D;JLT");
            }
            if (command.equals("gt")) {
                codeWrites("D;JGT");
            }
            codeWrites("D=0", "@" + falseLabel, "0;JMP");
            // 条件がTrueだった場合の遷移先
            codeWrites("(" + trueLabel + ")", "D=-1");
            // 条件がFalseだった場合の遷移先
            codeWrites("(" + falseLabel + ")");
            // スタックポインタが指すアドレスのデータを更新
            codeWrites("@SP", "A=M", "M=D");
        }

        // スタックポインタを更新
        incrementSP();
    }

    public void writePushPop(String commandType, String segment, int index) throws IOException {
        if (commandType.equals("C_PUSH")) {
            codeWrites("@" + index, "D=A");
            if (segment.equals("local")) {
                codeWrites("@LCL", "A=M+D", "D=M");
            }
            if (segment.equals("argument")) {
                codeWrites("@ARG", "A=M+D", "D=M");
            }
            if (segment.equals("this")) {
                codeWrites("@THIS", "A=M+D", "D=M");
            }
            if (segment.equals("that")) {
                codeWrites("@THAT", "A=M+D", "D=M");
            }
            if (segment.equals("temp")) {
                codeWrites("@5", "A=A+D", "D=M");
            }
            if (segment.equals("pointer")) {
                codeWrites("@3", "A=A+D", "D=M");
            }
            // Dレジスタ値をスタックに代入
            codeWrites("@SP", "A=M", "M=D");
            // スタックポインタを更新
            incrementSP();
        }

        if (commandType.equals("C_POP")) {
            // スタックポインタを更新
            decrementSP();
            // スタックをDレジスタに保存
            codeWrites("A=M", "D=M");
            // メモリセグメントのアドレスをAレジスタに格納
            if (segment.equals("local")) {
                codeWrites("@LCL", "A=M");
            }
            if (segment.equals("argument")) {
                codeWrites("@ARG", "A=M");
            }
            if (segment.equals("this")) {
                codeWrites("@THIS", "A=M");
            }
            if (segment.equals("that")) {
                codeWrites("@THAT", "A=M");
            }
            if (segment.equals("temp")) {
                codeWrites("@5");
            }
            if (segment.equals("pointer")) {
                codeWrites("@3");
            }
            for (int i = 0; i < index; i++) {
                codeWrites("A=A+1");
            }
            // メモリセグメントにDレジスタの値を格納
            codeWrites("M=D");
        }
    }

    public void close() throws IOException {
        bufferedWriter.close();
    }

    private void codeWrites(String... codes) throws IOException {
        for (String code : codes) {
            bufferedWriter.write(code);
            bufferedWriter.newLine();
        }
    }

    private void incrementSP() throws IOException {
        codeWrites("@SP", "M=M+1");
    }

    private void decrementSP() throws IOException {
        codeWrites("@SP", "M=M-1");
    }

    private String getLabel() throws IOException {
        String labelName = "LABEL" + labelNumber;
        labelNumber++;

        return labelName;
    }
}
