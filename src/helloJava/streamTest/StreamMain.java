package helloJava.streamTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamMain {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        Student student1 = new Student(100, "최수정");
        Student student2 = new Student(50, "아무개");

        Stream<Student> studentStream  = students.stream();

        IntStream scoreStream = studentStream.mapToInt(student -> student.getScore());
        double avg = scoreStream.average().getAsDouble();

        System.out.println(avg);
    }
}
