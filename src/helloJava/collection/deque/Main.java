package helloJava.collection.deque;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * offer, poll을 써야 용량 초과나 deque가 비었을 때 예외를 뱉지 않는다.
 * offer : 삽입, 용량 초과 시 flase 반환
 * poll : 삭제, deque 비었을 시 null 반환
 */
public class Main {
    public static void main(String[] args) {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.offerFirst(2);
        System.out.println(deque.pollLast());

        if (deque.pollLast() == null) {
            System.out.println("덱이 비었습니다.");
        }

    }




}
