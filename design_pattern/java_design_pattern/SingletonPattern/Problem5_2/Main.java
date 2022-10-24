package SingletonPattern.Problem5_2;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Triple triple = Triple.getInstance(i % 3);
            System.out.println(i + ":" + triple);
        }
    }
}
