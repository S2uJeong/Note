package helloJava.collectionTest;

import java.util.TreeSet;

public class TreeComparableExample {
    public static void main(String[] args) {
        TreeSet<Person> treeSet = new TreeSet<>();
        treeSet.add(new Person("최원규",60));
        treeSet.add(new Person("최수정", 26));
        treeSet.add(new Person("최인정", 29));
        for(Person person : treeSet) {
            System.out.println(person.name + " : " + person.age);
        }
    }
}

class Person implements Comparable<Person> {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Override
    public int compareTo(Person o) {
        if (this.age < o.age) return -1;
        else if(this.age > o.age) return 1;
        else return 0;
    }
}
