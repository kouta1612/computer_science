package implementations.VMtranslator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class CodeWriter {
    private static BufferedWriter bufferedWriter;
    private static int labelNumber = 0;
    private static int returnLabelNumber = 0;
    private static String vmFileName;
    private static String currentFunctionName;

    private static final Pattern NEGA_COMMAND_PATTERN = Pattern.compile("^(neg|not)$");
    private static final Pattern CALC_COMMAND_PATTERN = Pattern.compile("^(add|sub|and|or)$");
    private static final Pattern BOOL_COMMAND_PATTERN = Pattern.compile("^(eq|gt|lt)$");

    CodeWriter(File inFile) throws Exception {
        if (!inFile.exists()) {
            throw new FileNotFoundException("指定したファイルまたはディレクトリが見つかりませんでした。");
        }
        if (inFile.isFile() && !inFile.getPath().endsWith(".vm")) {
            throw new IllegalAccessError("VMファイルを指定してください。");
        }

        String outFileName = getOutFileName(inFile);
        File outFile = new File(outFileName);
        FileWriter fileWriter = new FileWriter(outFile);
        bufferedWriter = new BufferedWriter(fileWriter);
    }

    public void writeInit() throws Exception {
        // SP = 256
        codeWrites("@256", "D=A", "@SP", "M=D");
        // call Sys.init
        writeCall("Sys.init", 0);
    }

    public void setFileName(String filename) {
        vmFileName = filename;
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
            if (segment.equals("static")) {
                codeWrites("@" + vmFileName + "." + index, "D=M");
            } else {
                codeWrites("@" + index, "D=A");
            }

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
            if (segment.equals("static")) {
                codeWrites("@" + vmFileName + "." + index);
            }
            if (!segment.equals("static")) {
                for (int i = 0; i < index; i++) {
                    codeWrites("A=A+1");
                }
            }

            // メモリセグメントにDレジスタの値を格納
            codeWrites("M=D");
        }
    }

    public void writeLabel(String label) throws IOException {
        if (currentFunctionName == null) {
            codeWrites("(" + label + ")");
        } else {
            codeWrites("(" + currentFunctionName + "$" + label + ")");
        }
    }

    public void writeGoto(String label) throws IOException {
        if (currentFunctionName == null) {
            codeWrites("@" + label, "0;JMP");
        } else {
            codeWrites("@" + currentFunctionName + "$" + label, "0;JMP");
        }
    }

    public void writeIf(String label) throws IOException {
        decrementSP();
        codeWrites("@SP", "A=M", "D=M");
        if (currentFunctionName == null) {
            codeWrites("@" + label, "D;JNE");
        } else {
            codeWrites("@" + currentFunctionName + "$" + label, "D;JNE");
        }
    }

    public void writeCall(String functionName, int numArgs) throws IOException {
        currentFunctionName = functionName;
        // push return-address
        String returnLabel = getReturnLabel();
        codeWrites("@" + returnLabel, "D=A", "@SP", "A=M", "M=D");
        incrementSP();
        // push LCL
        codeWrites("@LCL", "D=M", "@SP", "A=M", "M=D");
        incrementSP();
        // push ARG
        codeWrites("@ARG", "D=M", "@SP", "A=M", "M=D");
        incrementSP();
        // push THIS
        codeWrites("@THIS", "D=M", "@SP", "A=M", "M=D");
        incrementSP();
        // push THAT
        codeWrites("@THAT", "D=M", "@SP", "A=M", "M=D");
        incrementSP();
        // ARG = SP - n - 5
        codeWrites("@SP", "D=M", "@" + numArgs, "D=D-A", "@5", "D=D-A");
        codeWrites("@ARG", "M=D");
        // LCL = SP
        codeWrites("@SP", "D=M", "@LCL", "M=D");
        // goto f
        codeWrites("@" + functionName, "0;JMP");
        // (return-address)
        codeWrites("(" + returnLabel + ")");
    }

    public void writeFunction(String functionName, int numLocals) throws IOException {
        codeWrites("(" + functionName + ")");
        for (int i = 0; i < numLocals; i++) {
            writePushPop("C_PUSH", "local", i);
        }
    }

    public void writeReturn() throws IOException {
        // FRAME = LCL
        codeWrites("@LCL", "D=M", "@FRAME", "M=D");
        // RET = *(FRAME - 5)
        codeWrites("@5", "D=A", "@FRAME", "A=M-D", "D=M", "@RET", "M=D");
        // *ARG = pop()
        codeWrites("@SP", "M=M-1", "A=M", "D=M", "@ARG", "A=M", "M=D");
        // SP = ARG + 1
        codeWrites("@ARG", "A=M+1", "D=A", "@SP", "M=D");
        // THAT = *(FRAME - 1)
        codeWrites("@FRAME", "A=M-1", "D=M", "@THAT", "M=D");
        // THIS = *(FRAME - 2)
        codeWrites("@2", "D=A", "@FRAME", "A=M-D", "D=M", "@THIS", "M=D");
        // ARG = *(FRAME - 3)
        codeWrites("@3", "D=A", "@FRAME", "A=M-D", "D=M", "@ARG", "M=D");
        // LCL = *(FRAME - 4)
        codeWrites("@4", "D=A", "@FRAME", "A=M-D", "D=M", "@LCL", "M=D");
        // goto RET
        codeWrites("@RET", "A=M", "0;JMP");
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

    private String getReturnLabel() throws IOException {
        String returnLabelName = "return-address" + returnLabelNumber;
        returnLabelNumber++;

        return returnLabelName;
    }

    private String getOutFileName(File inFile) {
        if (inFile.isFile()) {
            return inFile.getPath().replace(".vm", ".asm");
        }

        return inFile.getPath() + "/" + inFile.getName() + ".asm";
    }
}
