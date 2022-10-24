package FactoryMethodPattern.idcard;

import FactoryMethodPattern.framework.Product;

public class IDCard extends Product {
    private int id;
    private String owner;

    IDCard(String owner, int id) {
        System.out.println(owner + "(" + id + ")のカードを作ります。");
        this.owner = owner;
        this.id = id;
    }

    public void use() {
        System.out.println(this.owner + "(" + id + ")のカードを使います。");
    }

    public String getOwner() {
        return this.owner;
    }

    public int getId() {
        return this.id;
    }
}
