package helloJava.threadTest.start;

import helloJava.threadTest.util.MyLogger;
import helloJava.threadTest.util.ThreadUtils;

import static helloJava.threadTest.util.MyLogger.*;

public class StartTest4Main {
    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            while (true) {
                log("A");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "ThreadA");


        Thread threadB = new Thread(() -> {
            while (true) {
                log("B");
                ThreadUtils.sleep(500);
            }
        }, "ThreadB");

        threadA.start();
        threadB.start();
    }

}
