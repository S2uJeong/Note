package javaPractice.overrideTest;

public class Main {
    public static void main(String[] args) {
        Parent poly = new Child();
        poly.method();

        System.out.println("==================================");

        Parent poly2 = new Child();
        // poly2.childMethod();  메서드명이 다른, 자식에게만 있는 메서드 실행 불가
        poly2.method(); // 이건 자식걸로 나옴
        poly2.parentMethod();
        System.out.println("==================================");
        Child child = new Child();
        child.childMethod();
        child.parentMethod();
    }
}
