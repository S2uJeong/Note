package helloJava.threadTest;

import java.awt.*;

public class MutiThreadBeep {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                for (int i=0; i<5; i++) {
                    toolkit.beep();
                    try { Thread.sleep(2000);} catch (Exception e) {}
                }
            }
        });
        // 시작
        thread.start();
        for (int i=0; i<5; i++) {
            System.out.println("삥");
            try { Thread.sleep(2000);} catch (Exception e) {}
        }


    }
}
