package Power;

public class AttackPower {
    static final int MIN = 0;
    final int value;

    AttackPower(final int value) {
        if (value < MIN) {
            throw new IllegalArgumentException();
        }

        this.value = value;
    }

    AttackPower reinforce(final AttackPower increment) {
        return new AttackPower(this.value + increment.value);
    }

    AttackPower disable() {
        return new AttackPower(MIN);
    }
}