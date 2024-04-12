package javaPractice.thisTest;

public class Member {
    String name;
    int age;

    void initMember(String name, int age) {
        name = name;
        age = age;
    }

    void initThisMemner(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
