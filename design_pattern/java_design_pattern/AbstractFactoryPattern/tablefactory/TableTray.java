package AbstractFactoryPattern.tablefactory;

import java.util.Iterator;

import AbstractFactoryPattern.factory.Item;
import AbstractFactoryPattern.factory.Tray;

public class TableTray extends Tray {
    public TableTray(String caption) {
        super(caption);
    }

    public String makeHTML() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<td>\n");
        buffer.append("<table width=\"100%\" border=\"1\">\n");
        buffer.append("<tr>\n");
        buffer.append("<td bgcolor=\"#cccccc\" align=\"center\" colspan=\"" + tray.size() + "\">\n");
        buffer.append("<b>" + caption + "</b>\n");
        buffer.append("</td>\n");
        buffer.append("</tr>\n");
        buffer.append("<tr>\n");
        Iterator it = tray.iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            buffer.append(item.makeHTML());
        }
        buffer.append("</tr>\n");
        buffer.append("</table>\n");
        buffer.append("</td>\n");
        return buffer.toString();
    }
}
