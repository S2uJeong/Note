package helloJava.threadTest.util;

public abstract class ThreadUtils {
    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            MyLogger.log("인터럽트 발생" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

