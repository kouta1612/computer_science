package AbstractFactoryPattern.factory;

public abstract class Factory {
    public static Factory getFactory(String className) {
        Factory factory = null;
        try {
            factory = (Factory) Class.forName(className).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return factory;
    }

    public abstract Link makeLink(String caption, String url);

    public abstract Tray makeTray(String caption);

    public abstract Page makePage(String title, String authror);
}
