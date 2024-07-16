package helloJava.threadTest.sychronized;

import static java.lang.Thread.*;

public class WorkObject {
    public synchronized void methodA() {
        System.out.println(currentThread().getName() + ": methodA 작업 실행");
        notify(); // 다른 스레드를 실행 대기 상태로
        try {
            wait(); // 자신의 스레드는 일시 정지 상태로
        } catch (InterruptedException e) {
        }
    }
    public synchronized void methodB() {
        System.out.println(currentThread().getName() + ": methodB 작업 실행");
        notify(); // 다른 스레드를 실행 대기 상태로
        try {
            wait(); // 자신의 스레드는 일시 정지 상태로
        } catch (InterruptedException e) {
        }
    }
}
