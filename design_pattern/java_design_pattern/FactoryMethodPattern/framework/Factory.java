package FactoryMethodPattern.framework;

public abstract class Factory {
    public final Product create(String owner) {
        Product p = this.createProduct(owner);
        this.registerProduct(p);
        return p;
    }

    protected abstract Product createProduct(String owner);

    protected abstract void registerProduct(Product product);
}
