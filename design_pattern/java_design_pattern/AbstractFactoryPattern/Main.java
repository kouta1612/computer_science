package AbstractFactoryPattern;

import AbstractFactoryPattern.factory.Factory;
import AbstractFactoryPattern.factory.Link;
import AbstractFactoryPattern.factory.Page;
import AbstractFactoryPattern.factory.Tray;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main class.name.of.ConcreateFactory");
            System.out.println("Example1: java Main AbstractFactoryPattern.listfactory.ListFactory");
            System.out.println("Example1: java Main AbstractFactoryPattern.tablefactory.TableFactory");
            System.exit(1);
        }
        Factory factory = Factory.getFactory(args[0]);
        Link asahi = factory.makeLink("朝日新聞", "http://www.asahi.com/");
        Link yomiuri = factory.makeLink("読売新聞", "http://www.yomiuri.co.jp/");

        Link us_yahoo = factory.makeLink("Yahoo!", "http://www.yahoo.com/");
        Link jp_yahoo = factory.makeLink("Yahoo!Japan", "http://www.yahoo.co.jp/");
        Link excite = factory.makeLink("Excite", "http://www.excite.com/");
        Link google = factory.makeLink("Google", "http://www.google.com/");

        Tray trayNews = factory.makeTray("新聞");
        trayNews.add(asahi);
        trayNews.add(yomiuri);

        Tray trayYahoo = factory.makeTray("Yahoo!");
        trayYahoo.add(us_yahoo);
        trayYahoo.add(jp_yahoo);

        Tray traySearch = factory.makeTray("サーチエンジン");
        traySearch.add(trayYahoo);
        traySearch.add(excite);
        traySearch.add(google);

        Page page = factory.makePage("LinkPage", "萩原孔太");
        page.add(trayNews);
        page.add(traySearch);
        page.output();
    }
}
