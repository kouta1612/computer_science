package Power;

public class Weapon {
    final AttackPower attackPower;

    Weapon(final AttackPower attackPower) {
        this.attackPower = attackPower;
    }

    Weapon reinforce(final AttackPower increment) {
        final AttackPower reinforced = this.attackPower.reinforce(increment);
        return new Weapon(reinforced);
    }
}
