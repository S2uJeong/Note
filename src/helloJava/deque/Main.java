package helloJava.deque;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        Deque<Integer> linkDeque = new LinkedList<>();
        // 앞에 요소 추가
        linkDeque.addFirst(1);
        linkDeque.addFirst(2);

        // 뒤에 요소 추가
        linkDeque.addLast(3);
        linkDeque.addLast(4);

        // 앞 요소 제거 및 반환
        System.out.println("Removed from front: " + linkDeque.removeFirst());

        // 뒤 요소 제거 및 반환
        System.out.println("Removed from end: " + linkDeque.removeLast());

        // 앞 요소 확인
        System.out.println("Front element: " + linkDeque.getFirst());

        // 뒤 요소 확인
        System.out.println("End element: " + linkDeque.getLast());



        Deque<Integer> arrDeque = new ArrayDeque<>();

        // 앞에 요소 추가
        arrDeque.addFirst(1);
        arrDeque.addFirst(2);

        // 뒤에 요소 추가
        arrDeque.addLast(3);

        // 앞 요소 제거 및 반환
        System.out.println("Removed from front: " + arrDeque.removeFirst());

        // 뒤 요소 제거 및 반환
        System.out.println("Removed from end: " + arrDeque.removeLast());

        // 앞 요소 확인
        System.out.println("Front element: " + arrDeque.getFirst());

        // 뒤 요소 확인
        System.out.println("End element: " + arrDeque.getLast());
    }
}
