package Power;

public class Main {
    public static void main(String[] args) {
        final AttackPower attackPowerA = new AttackPower(20);
        final AttackPower attackPowerB = new AttackPower(20);

        final Weapon weaponA = new Weapon(attackPowerA);
        final Weapon weaponB = new Weapon(attackPowerB);

        final AttackPower increment = new AttackPower(5);
        final Weapon reinforcedWeaponA = weaponA.reinforce(increment);

        System.out.println(attackPowerA.value);
        System.out.println(reinforcedWeaponA.attackPower.value);
        System.out.println(weaponB.attackPower.value);
    }
}
