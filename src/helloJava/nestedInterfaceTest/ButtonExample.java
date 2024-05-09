package helloJava.nestedInterfaceTest;


public class ButtonExample {
    public static void main(String[] args) {
        /**
         * ok 버튼
         */
        // 버튼 객체 생성
        Button bntok = new Button();
        // 버튼 클릭 이벤트를 처리할 구현 클래스 (==로컬 클래스)
        class OkListener implements Button.ClickListener {
            @Override
            public void onClick() {
                System.out.println("ok버튼을 눌렀습니다.");
            }
        }
        // 버튼 객체에 구현 객체 주입
        bntok.setClickListener(new OkListener());
        bntok.click();
        /**
         * cancle 버튼
         */
        Button bntCancel = new Button();

        class CancelListener implements Button.ClickListener {
            @Override
            public void onClick() {
                System.out.println("취소 버튼을 눌렀습니다.");
            }
        }

        bntCancel.setClickListener(new CancelListener());
        bntCancel.click();

    }
}
