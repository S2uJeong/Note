package helloJava.sort;

import java.util.Arrays;

public class BinaryTest {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6};
        int idx = Arrays.binarySearch(a, 10);
        System.out.println(idx);
    }
}
