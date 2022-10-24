package SingletonPattern;

public class Singleton {
    private static Singleton singleton = new Singleton();

    private Singleton() {
        System.out.println("シングルトンを生成しました。");
    }

    public static Singleton getSingleton() {
        return singleton;
    }
}
