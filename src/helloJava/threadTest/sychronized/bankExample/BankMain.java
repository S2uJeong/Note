package helloJava.threadTest.sychronized.bankExample;

import helloJava.threadTest.util.ThreadUtils;

import static helloJava.threadTest.util.MyLogger.log;

public class BankMain {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccountV2(1000);

        Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
        Thread t2 = new Thread(new WithdrawTask(account, 800), "t2");
        t1.start();
        t2.start();

        ThreadUtils.sleep(500);
        log("t1 status : " + t1.getState());
        log("t2 status : " + t2.getState());

        t1.join();
        t2.join();
        log("최종 잔액: " + account.getBalance());


    }
}
