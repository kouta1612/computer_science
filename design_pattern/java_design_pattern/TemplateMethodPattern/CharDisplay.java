package TemplateMethodPattern;

public class CharDisplay extends AbstractDisplay {
    private char ch;

    public CharDisplay(char ch) {
        this.ch = ch;
    }

    public void open() {
        System.out.print("<<");
    }

    public void print() {
        System.out.print(this.ch);
    }

    public void close() {
        System.out.println(">>");
    }
}
