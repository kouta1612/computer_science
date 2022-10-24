package Attack;

public class ExtendPhysicalAttack extends PhysicalAttack {
    int singleAttackDamage() {
        return super.singleAttackDamage() + 20;
    }

    int dobleAttackDamage() {
        return super.dobleAttackDamage() + 10;
    }
}
