package AbstractFactoryPattern.tablefactory;

import AbstractFactoryPattern.factory.Factory;

public class TableFactory extends Factory {
    public TableLink makeLink(String caption, String url) {
        return new TableLink(caption, url);
    }

    public TableTray makeTray(String caption) {
        return new TableTray(caption);
    }

    public TablePage makePage(String title, String authror) {
        return new TablePage(title, authror);
    }
}
