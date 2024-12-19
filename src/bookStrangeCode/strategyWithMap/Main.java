package bookStrangeCode.strategyWithMap;


public class Main {
    public static void main(String[] args) {
        
        final Member member = new Member(2);
        MagicMemberPolicy magicMemberPolicy = new MagicMemberPolicy(member);

        magicMemberPolicy.magicAttack(MagicType.fire);

        magicMemberPolicy.magicAttack(MagicType.lightning);
        
        
    }
}
