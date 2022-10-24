package Money;

import java.util.Currency;

public class Money {
    final int amount;
    final Currency currency;

    Money(final int amount, final Currency currency) {
        if (amount < 0) {
            throw new IllegalArgumentException("金額には0以上を指定してください。");
        }
        if (currency == null) {
            throw new NullPointerException("通過単位を指定してください。");
        }

        this.amount = amount;
        this.currency = currency;
    }

    Money add(final Money other) {
        final int added = amount + other.amount;
        return new Money(added, currency);
    }
}