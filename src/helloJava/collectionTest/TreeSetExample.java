package helloJava.collectionTest;

import java.util.*;

/**
 * 무작위로 저장한 점수를 검색한다.
 */
public class TreeSetExample {
    public static void main(String[] args) {
        TreeSet<Integer> scores = new TreeSet<>();
        for(int i=30; i<100; i+=10) {
            scores.add(i);
        }
        forPrint(scores);

        System.out.println("======= 특정 객체 검색 ======");
        System.out.println("가장 낮은 점수 : " + scores.first());
        System.out.println("가장 높은 점수 : " + scores.last());
        System.out.println("80점 아래 점수 : " + scores.lower(80));
        System.out.println("80점 위 점수 : " + scores.higher(80));
        System.out.println("80점 아래 점수 : " + scores.lower(80));
        System.out.println("85점이거나 바로 아래 점수 : " + scores.floor(85));
        System.out.println("85점이거나 바로 위 점수 : " + scores.ceiling(85));

        System.out.println("======= 내림차순 정렬 ======");
        NavigableSet<Integer> descendingScores = scores.descendingSet();
        forPrint(descendingScores);

        System.out.println("======== 범위 검색 ( 70 <= ) ======== ");
        NavigableSet<Integer> rangeSet = scores.tailSet(70,true);
        forPrint(rangeSet);

        System.out.println("======== 범위 검색 ( 40 <= score < 90 ) ======== ");
        rangeSet = scores.subSet(40, true, 90, false);
        forPrint(rangeSet);
    }
    private static <T> void forPrint(Collection<T> sets) {
        for(T t : sets) {
            System.out.print(t + " ");
        }
        System.out.println();
    }
}
