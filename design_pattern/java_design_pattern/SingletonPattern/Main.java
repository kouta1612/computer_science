package SingletonPattern;

public class Main {
    public static void main(String[] args) {
        Singleton obj1 = Singleton.getSingleton();
        Singleton obj2 = Singleton.getSingleton();
        if (obj1 == obj2) {
            System.out.println("obj1とobj2は同じインスタンスです。");
        } else {
            System.out.println("obj1とobj2は違うインスタンスです。");
        }
    }
}
