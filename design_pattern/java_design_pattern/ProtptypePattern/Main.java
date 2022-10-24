package ProtptypePattern;

import ProtptypePattern.framework.Manager;
import ProtptypePattern.framework.Product;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.register("box", new MessageBox('*'));
        manager.register("line", new UnderlinePen('~'));
        Product box = manager.create("box");
        Product line = manager.create("line");
        box.use("Hello, World");
        line.use("Hello, World");
    }
}
