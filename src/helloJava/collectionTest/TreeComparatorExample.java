package helloJava.collectionTest;

import java.util.Comparator;
import java.util.TreeSet;

public class TreeComparatorExample {
    public static void main(String[] args) {
        // 비교자를 의존한 TreeSet 컬렉션 생성
        TreeSet<Fruit> treeSet = new TreeSet<>(new FruitComparator());

        treeSet.add(new Fruit("귤", 10_000));
        treeSet.add(new Fruit("사과", 20_000));
        treeSet.add(new Fruit("수박", 15_000));

        for(Fruit fruit : treeSet) {
            System.out.println(fruit.name + " : " + fruit.price);
        }
    }
}

class Fruit {
    String name;
    int price;

    public Fruit(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

class FruitComparator implements Comparator<Fruit> {
    @Override
    public int compare(Fruit o1, Fruit o2) {
        if(o1.price < o2.price) return -1;
        else if(o1.price == o2.price) return 0;
        else return 1;
    }
}
