package Attack;

public class Main {
    public static void main(String[] args) {
        // 委譲
        FighterPhysicalAttack fighterPhysicalAttack = new FighterPhysicalAttack();
        System.out.println(fighterPhysicalAttack.dobleAttackDamage());

        // 継承
        PhysicalAttack parentPhysicalAttack = new PhysicalAttack();
        System.out.println(parentPhysicalAttack.dobleAttackDamage());

        // 継承
        PhysicalAttack childPhysicalAttack = new ExtendPhysicalAttack();
        System.out.println(childPhysicalAttack.dobleAttackDamage());

        // 継承
        ExtendPhysicalAttack extendPhysicalAttack = new ExtendPhysicalAttack();
        System.out.println(extendPhysicalAttack.dobleAttackDamage());
    }
}
