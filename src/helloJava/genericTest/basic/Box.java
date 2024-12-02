package helloJava.genericTest.basic;

public class Box<T, E> {
    private final T category;
    private final E content;

    public Box(T category, E content) {
        this.category = category;
        this.content = content;
    }

    public void print() {
        System.out.println("category is : " + category + ", content is : " + content);
    }
}
