package helloJava.threadTest.bounded;

import helloJava.threadTest.util.MyLogger;

import java.util.ArrayList;
import java.util.List;

import static helloJava.threadTest.util.MyLogger.log;

public class BoundedMain {
    public static void main(String[] args) {
        BoundedQueue queue = new BoundedQueueV1(2);

    }

    private static void producerFirst(BoundedQueue queue) {
        log(" == [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName());
        List<Thread> threads = new ArrayList<>();


    }
}
