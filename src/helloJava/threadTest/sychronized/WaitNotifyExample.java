package helloJava.threadTest.sychronized;

public class WaitNotifyExample {
    public static void main(String[] args) {
        WorkObject workObject = new WorkObject();

        Thread threadA = new ThreadA(workObject);
        Thread threadB = new ThreadB(workObject);

        threadA.start();
        threadB.start();

    }
}
