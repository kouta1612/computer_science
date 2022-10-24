package AbstractFactoryPattern.listfactory;

import AbstractFactoryPattern.factory.Link;

public class ListLink extends Link {
    public ListLink(String caption, String url) {
        super(caption, url);
    }

    public String makeHTML() {
        return " <li><a href=\"" + url + "\">" + caption + "</a></li>";
    }
}
