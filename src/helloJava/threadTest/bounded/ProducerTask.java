package helloJava.threadTest.bounded;

import helloJava.threadTest.util.MyLogger;

public class ProducerTask implements Runnable{
    private BoundedQueue queue;
    private String request;

    public ProducerTask(BoundedQueue queue, String request) {
        this.queue = queue;
        this.request = request;
    }

    @Override
    public void run() {
        MyLogger.log("[생산 시도]" + request + "->" + queue);
        queue.put(request);
        MyLogger.log("[생산 완료]" + request + "->" + queue);

    }
}
