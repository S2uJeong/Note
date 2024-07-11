package helloJava.exceptionTest;

public class Main {
    public static void main(String[] args) {
        String[] strArr = {"100", "1oo"};
        for (int i = 0; i <= strArr.length; i++) {
            try {
                int val = Integer.parseInt(strArr[i]);
                System.out.println(val);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ArrayIndexOutOfBoundsException");
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException");
            } catch (Exception e) {
                System.out.println("Exception");
            }
        }
    }
}


