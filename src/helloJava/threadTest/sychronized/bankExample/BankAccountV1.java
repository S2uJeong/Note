package helloJava.threadTest.sychronized.bankExample;

import helloJava.threadTest.util.MyLogger;
import helloJava.threadTest.util.ThreadUtils;

import static helloJava.threadTest.util.MyLogger.log;

public class BankAccountV1 implements BankAccount {
    private int balance;

    public BankAccountV1(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public synchronized boolean withdraw(int amount) {
        log("거래 시작 : " + getClass().getSimpleName());
        log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
        if (balance < amount) {
            log("[검증 실패] 출금액: " + amount + ", 잔액 " + balance);
            return false;
        }
        log("[검증 완료] 출금액: " + amount + ", 잔액 " + balance);
        ThreadUtils.sleep(1000);
        balance = balance - amount;
        log("[출금 완료] 출금액: " + amount + ", 변경 잔액 " + balance);
        log("거래종료");
        return false;
    }

    @Override
    public synchronized int getBalance() {
        return balance;
    }
}
