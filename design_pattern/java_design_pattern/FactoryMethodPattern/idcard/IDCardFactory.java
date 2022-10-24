package FactoryMethodPattern.idcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import FactoryMethodPattern.framework.Factory;
import FactoryMethodPattern.framework.Product;

public class IDCardFactory extends Factory {
    private HashMap map = new HashMap();
    private int id = 100;

    protected Product createProduct(String owner) {
        return new IDCard(owner, this.id++);
    }

    protected void registerProduct(Product product) {
        IDCard idCard = (IDCard) product;
        this.map.put(Integer.valueOf(idCard.getId()), idCard.getOwner());
    }

    public HashMap getMaps() {
        return this.map;
    }
}
