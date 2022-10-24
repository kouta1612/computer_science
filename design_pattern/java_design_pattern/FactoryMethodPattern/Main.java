package FactoryMethodPattern;

import FactoryMethodPattern.framework.Factory;
import FactoryMethodPattern.framework.Product;
import FactoryMethodPattern.idcard.IDCardFactory;

public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("萩原");
        Product card2 = factory.create("鈴木");
        Product card3 = factory.create("高井");
        card1.use();
        card2.use();
        card3.use();
    }
}
