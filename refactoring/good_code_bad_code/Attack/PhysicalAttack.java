package Attack;

public class PhysicalAttack {
    int singleAttackDamage() {
        return 10;
    }

    int dobleAttackDamage() {
        return singleAttackDamage() + singleAttackDamage();
    }
}