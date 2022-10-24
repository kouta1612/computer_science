package AbstractFactoryPattern.listfactory;

import java.util.Iterator;

import AbstractFactoryPattern.factory.Item;
import AbstractFactoryPattern.factory.Tray;

public class ListTray extends Tray {
    public ListTray(String caption) {
        super(caption);
    }

    public String makeHTML() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<li>\n");
        buffer.append(caption);
        buffer.append("<ul>\n");
        Iterator it = tray.iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            buffer.append(item.makeHTML());
        }
        buffer.append("</ul>\n");
        buffer.append("</li>\n");
        return buffer.toString();
    }
}
