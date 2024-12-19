package bookStrangeCode.strategyWithMap;

import java.util.HashMap;
import java.util.Map;

public class MagicMemberPolicy {
    private final Map<MagicType, Magic> magics = new HashMap<>();
    public MagicMemberPolicy(final Member member) {
        final Fire fire = new Fire(member);
        final Lightning lightning = new Lightning(member);
        magics.put(MagicType.fire, fire);
        magics.put(MagicType.lightning, lightning);
    }
    void magicAttack(final MagicType magicType) {
        final Magic usingMasic = magics.get(magicType);
        showMagicName(usingMasic);
        magicDamage(usingMasic);
    }

    private void magicDamage(final Magic magic) {
        final AttackPower attackPower = magic.attackPower();
        System.out.println(attackPower.getValue());
    }

    private void showMagicName(final Magic magic) {
        final String name = magic.name();
        System.out.println(name);
    }


}
