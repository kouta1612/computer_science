package BridgePattern;

import BridgePattern.function.CountDisplay;
import BridgePattern.function.Display;
import BridgePattern.implementation.StringDisplayImpl;

public class Main {
    public static void main(String[] args) {
        Display disp1 = new Display(new StringDisplayImpl("Hello, Japan"));
        Display disp2 = new Display(new StringDisplayImpl("Hello, World"));
        CountDisplay disp3 = new CountDisplay(new StringDisplayImpl("Hello, World"));
        disp1.display();
        disp2.display();
        disp3.display();
        disp3.multiDisplay(5);
    }
}
