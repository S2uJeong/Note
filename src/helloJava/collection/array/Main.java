package helloJava.collection.array;


public class Main {
    public static void main(String[] args) {
        int[] a;  // 선언
        a = new int[5];  // 참조
        int[] b1 = {1,2,3,45,5}; // 배열 선언 및 초기화

        int[] cloneA = b1.clone(); // 깊은 복사
        cloneA[1] = 12345;
        printArray(cloneA, "cloneA");
        printArray(b1, "b1");
    }

    public static void printArray(int[] array, String arrayName) {
        System.out.print(arrayName + ": ");
        for (int a : array) {
            System.out.print(" " + a);
        }
        System.out.println();
    }
}
