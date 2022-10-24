package AbstractFactoryPattern.listfactory;

import AbstractFactoryPattern.factory.Factory;
import AbstractFactoryPattern.factory.Link;
import AbstractFactoryPattern.factory.Page;
import AbstractFactoryPattern.factory.Tray;

public class ListFactory extends Factory {
    public Link makeLink(String caption, String url) {
        return new ListLink(caption, url);
    }

    public Tray makeTray(String caption) {
        return new ListTray(caption);
    }

    public Page makePage(String title, String author) {
        return new ListPage(title, author);
    }
}
