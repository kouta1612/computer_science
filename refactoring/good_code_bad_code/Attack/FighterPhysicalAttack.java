package Attack;

public class FighterPhysicalAttack {
    private final PhysicalAttack _physicalAttack;

    FighterPhysicalAttack() {
        _physicalAttack = new PhysicalAttack();
    }

    int singleAttackDamage() {
        return _physicalAttack.singleAttackDamage() + 20;
    }

    int dobleAttackDamage() {
        return _physicalAttack.dobleAttackDamage() + 10;
    }
}
