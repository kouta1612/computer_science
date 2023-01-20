package implementations.assembler;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalAccessException("第一引数にファイルパスが設定されていません。");
        }

        Parser parser = new Parser(args[0]);
        while (true) {
            parser.advance();
            String line = parser.getCommand();
            if (line == null) {
                break;
            }
            if (!line.equals("")) {
                switch (parser.commandType()) {
                    case "C_COMMAND":
                        System.out.println(line + " (dest: " + parser.dest() + ", comp: " +
                                parser.comp() + ", jump: "
                                + parser.jump() + ")");
                        break;
                    case "A_COMMAND":
                        System.out.println(line + " (" + parser.symbol() + ")");
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
