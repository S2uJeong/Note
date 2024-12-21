package bookStrangeCode.goodComment;

public class Main {
    public static void main(String[] args) {

        States states = new States();
        Member member = new Member(states);

        states.addState(new State(StateType.POISON));
        System.out.println("고통 데미지를 추가 해야 하는가? : expect = ture, result = " + member.isPainful());

        states.removeState(new State(StateType.POISON));
        System.out.println("고통 데미지를 추가 해야 하는가? : expect = false, result = " + member.isPainful());
    }
}
