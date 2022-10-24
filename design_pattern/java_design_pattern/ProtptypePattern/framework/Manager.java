package ProtptypePattern.framework;

import java.util.HashMap;

public class Manager {
    private HashMap showcase = new HashMap();

    public void register(String name, Product proto) {
        showcase.put(name, proto);
    }

    public Product create(String protoname) {
        Product proto = (Product) showcase.get(protoname);
        return proto.createClone();
    }
}
