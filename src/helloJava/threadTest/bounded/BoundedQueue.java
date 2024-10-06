package helloJava.threadTest.bounded;

public interface BoundedQueue {
    void put(String data);
    String take();
}
