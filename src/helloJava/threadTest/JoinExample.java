package helloJava.threadTest;

public class JoinExample {
    public static void main(String[] args) {
        SumThread sumThred = new SumThread();
        sumThred.start();
        System.out.println("작업 스레드 실행시킴");
        System.out.println("join 전의 합: " + sumThred.getSum());
        try {
            sumThred.join();
        } catch (InterruptedException e) {
        }
        System.out.println("1~100의 합 : " + sumThred.getSum());
    }
}

class SumThread extends Thread {
    private long sum;

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        for(int i=1; i<=100; i++) {
            sum += i;
        }
    }
}
