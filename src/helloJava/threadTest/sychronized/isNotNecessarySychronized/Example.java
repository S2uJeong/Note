package helloJava.threadTest.sychronized.isNotNecessarySychronized;

import helloJava.threadTest.util.MyLogger;

/**
 * 지역변수는 스레드별로 공유되지 않는다.
 * 따라서 sychronized를 불필요하게 사용하지 않게 유의
 */
public class Example {
    public static void main(String[] args) {
        MyCounter myCounter = new MyCounter();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                myCounter.count();
            }
        };

        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();

    }
    static class MyCounter {
        public void count() {
            int localValue = 0;
            for (int i = 0; i < 1000; i++) {
                localValue += 1;
            }
            MyLogger.log("결과: " + localValue);
        }
    }
}
