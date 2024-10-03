package helloJava.threadTest.sychronized.bankExample;

import helloJava.threadTest.util.ThreadUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static helloJava.threadTest.util.MyLogger.log;

public class BankAccountUseReentrantLockV2 implements BankAccount {
    private int balance;

    private final Lock lock = new ReentrantLock();

    public BankAccountUseReentrantLockV2(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작 : " + getClass().getSimpleName());

        if (!lock.tryLock()) {
            log("[진입 실패] 이미 처리중인 작업이 있습니다.");
            return false;
        }

        try {
            log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                log("[검증 실패] 출금액: " + amount + ", 잔액 " + balance);
                return false;
            }
            log("[검증 완료] 출금액: " + amount + ", 잔액 " + balance);
            ThreadUtils.sleep(1000);
            balance = balance - amount;
            log("[출금 완료] 출금액: " + amount + ", 변경 잔액 " + balance);
        } finally {
            lock.unlock();
        }
        log("거래종료");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
