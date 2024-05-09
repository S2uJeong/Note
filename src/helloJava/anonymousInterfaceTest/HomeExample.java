package helloJava.anonymousInterfaceTest;

public class HomeExample {
    public static void main(String[] args) {
        Home home = new Home();
        // 필드 이용
        home.use1();
        // 로컬 변수 이용
        home.use2();
        // 매개변수 이용
        home.use3(new RemoteControl() {
            @Override
            public void turnOn() {
                System.out.println("난방을 켭니다.");
            }

            @Override
            public void turnOff() {
                System.out.println("난방을 끕니다.");
            }
        });
    }

}
