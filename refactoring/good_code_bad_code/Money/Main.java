package Money;

import java.util.Currency;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Currency yen = Currency.getInstance(Locale.JAPAN);
        Money money = new Money(1, yen);
        Money otherMoney = new Money(100, yen);
        Money addedMoney = money.add(otherMoney);
        System.out.println(addedMoney.amount);
    }
}
