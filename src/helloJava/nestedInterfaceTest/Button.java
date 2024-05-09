package helloJava.nestedInterfaceTest;

public class Button {
    public static interface ClickListener {
        void onClick(); }
    // 중첩 인터페이스타입 필드
    private ClickListener clickListener;
    // setter
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    // 인터페이스 메서드 실행시키는 메서드 (정확히는 setter로 주입된 구현체가 정의한 onClick())
    public void click() {
        this.clickListener.onClick();
    }
}
