package helloJava.thisTest;

public class MemberMain {
    public static void main(String[] args) {
        Member member = new Member();
        member.initMember("수정", 26 );

        System.out.println(member.name);
        System.out.println(member.age);

        System.out.println("===== This 사용 =====");
        member.initThisMemner("수정", 26);
        System.out.println(member.name);
        System.out.println(member.age);
    }
}
