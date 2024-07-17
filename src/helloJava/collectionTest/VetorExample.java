package helloJava.collectionTest;
import java.util.*;

public class VetorExample {
    public static void main(String[] args) {
        List<Board> list = new Vector<>();
        //List<Board> list = new ArrayList<>();  // 경합이 발생함

        Thread threadA = new Thread() {
            @Override
            public void run() {
                for (int i = 1; i <= 1000; i++) {
                    list.add(new Board("제목" + i, "내용" + i, "글쓴이" + i));
                }
            }
        };

        Thread threadB = new Thread() {
            @Override
            public void run() {
                for (int i = 1001; i <= 2000; i++) {
                    list.add(new Board("제목" + i, "내용" + i, "글쓴이" + i));
                }
            }
        };

        threadA.start();
        threadB.start();
        // 작업 스레드들이 모두 종료될 때까지 메인 스레드 대기
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
        }

        int size = list.size();
        System.out.println("총 객체 수 : " + size);

    }
}

class Board {
    String subject;
    String content;
    String writer;

    public Board(String subject, String content, String writer) {
        this.subject = subject;
        this.content = content;
        this.writer = writer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}