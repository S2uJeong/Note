package helloJava.threadTest.bounded;

import helloJava.threadTest.util.ThreadUtils;

import java.util.ArrayDeque;
import java.util.Queue;

import static helloJava.threadTest.util.MyLogger.log;

/**
 * 큐에 빈 공간이나 자료가 생기는지 주기적으로 체크하며 대기한다.
 */
public class BoundedQueueV2 implements BoundedQueue{
    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV2(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            ThreadUtils.sleep(1000);
        }
        queue.offer(data);
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            ThreadUtils.sleep(1000);
        }
        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
