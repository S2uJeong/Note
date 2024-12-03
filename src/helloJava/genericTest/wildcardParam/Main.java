package helloJava.genericTest.wildcardParam;

public class Main {
    public static void main(String[] args) {
        // 모든 사람이 신청가능
        Course.registerCourse1(new Applicant<Person>(new Person()));
        Course.registerCourse1(new Applicant<Worker>(new Worker()));
        // 학생만 가능
        Course.registerCourse2(new Applicant<Student>(new Student()));
        // Course.registerCourse2(new Applicant<Worker>(new Worker())); // 불가

    }
}
