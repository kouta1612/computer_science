package TemplateMethodPattern;

public class StringDisplay extends AbstractDisplay {
    private String string;
    private int width;

    public StringDisplay(String string) {
        this.string = string;
        this.width = string.getBytes().length;
    }

    public void open() {
        this.printLine();
    }

    public void print() {
        System.out.println("|" + this.string + "|");
    }

    public void close() {
        this.printLine();
    }

    private void printLine() {
        System.out.print("+");
        for (int i = 0; i < this.width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
