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
            bufferedWriter.write("A=M");
            bufferedWriter.newLine();
            if (command.equals("neg")) {
                bufferedWriter.write("M=-M");
            }
            if (command.equals("not")) {
                bufferedWriter.write("M=!M");
            }
            bufferedWriter.newLine();
        }
        if (CALC_COMMAND_PATTERN.matcher(command).matches()) {
            // スタックポインタを更新
            decrementSP();
            // Dレジスタにスタックを代入
            bufferedWriter.write("A=M");
            bufferedWriter.newLine();
            bufferedWriter.write("D=M");
            bufferedWriter.newLine();
            // スタックポインタを更新
            decrementSP();

            // 演算を実行
            bufferedWriter.write("A=M");
            bufferedWriter.newLine();
            if (command.equals("add")) {
                bufferedWriter.write("M=M+D");
            }
            if (command.equals("sub")) {
                bufferedWriter.write("M=M-D");
            }
            if (command.equals("and")) {
                bufferedWriter.write("M=M&D");
            }
            if (command.equals("or")) {
                bufferedWriter.write("M=M|D");
            }
            bufferedWriter.newLine();
        }

        if (BOOL_COMMAND_PATTERN.matcher(command).matches()) {
            // スタックポインタを更新
            decrementSP();
            // Dレジスタにスタックを代入
            bufferedWriter.write("A=M");
            bufferedWriter.newLine();
            bufferedWriter.write("D=M");
            bufferedWriter.newLine();
            // スタックポインタを更新
            decrementSP();

            // 演算を実行
            bufferedWriter.write("A=M");
            bufferedWriter.newLine();
            bufferedWriter.write("D=M-D");
            bufferedWriter.newLine();
            String trueLabel = getLabel();
            String falseLabel = getLabel();
            // 条件によって遷移先に移動
            bufferedWriter.write("@" + trueLabel);
            bufferedWriter.newLine();
            if (command.equals("eq")) {
                bufferedWriter.write("D;JEQ");
            }
            if (command.equals("lt")) {
                bufferedWriter.write("D;JLT");
            }
            if (command.equals("gt")) {
                bufferedWriter.write("D;JGT");
            }
            bufferedWriter.newLine();
            bufferedWriter.write("D=0");
            bufferedWriter.newLine();
            bufferedWriter.write("@" + falseLabel);
            bufferedWriter.newLine();
            bufferedWriter.write("0;JMP");
            bufferedWriter.newLine();
            // 条件がTrueだった場合の遷移先
            bufferedWriter.write("(" + trueLabel + ")");
            bufferedWriter.newLine();
            bufferedWriter.write("D=-1");
            bufferedWriter.newLine();
            // 条件がFalseだった場合の遷移先
            bufferedWriter.write("(" + falseLabel + ")");
            bufferedWriter.newLine();
            // スタックポインタが指すアドレスのデータを更新
            bufferedWriter.write("@SP");
            bufferedWriter.newLine();
            bufferedWriter.write("A=M");
            bufferedWriter.newLine();
            bufferedWriter.write("M=D");
            bufferedWriter.newLine();
        }

        // スタックポインタを更新
        incrementSP();
    }

    public void writePushPop(String commandType, String segment, int index) throws IOException {
        if (commandType.equals("C_PUSH")) {
            if (segment.equals("constant")) {
                // 定数をDレジスタに代入
                bufferedWriter.write("@" + index);
                bufferedWriter.newLine();
                bufferedWriter.write("D=A");
                bufferedWriter.newLine();
                // Dレジスタ値をスタックに代入
                bufferedWriter.write("@SP");
                bufferedWriter.newLine();
                bufferedWriter.write("A=M");
                bufferedWriter.newLine();
                bufferedWriter.write("M=D");
                bufferedWriter.newLine();
                // スタックポインタを更新
                incrementSP();
            }
        }
    }

    public void close() throws IOException {
        bufferedWriter.close();
    }

    private void incrementSP() throws IOException {
        bufferedWriter.write("@SP");
        bufferedWriter.newLine();
        bufferedWriter.write("M=M+1");
        bufferedWriter.newLine();
    }

    private void decrementSP() throws IOException {
        bufferedWriter.write("@SP");
        bufferedWriter.newLine();
        bufferedWriter.write("M=M-1");
        bufferedWriter.newLine();
    }

    private String getLabel() throws IOException {
        String labelName = "LABEL" + labelNumber;
        labelNumber++;

        return labelName;
    }
}
