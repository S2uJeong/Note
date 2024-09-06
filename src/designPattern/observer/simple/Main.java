package designPattern.observer.simple;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SimpleSubject simpleSubject = new SimpleSubject();

        SimpleObserver simpleObserver = new SimpleObserver(simpleSubject);

        simpleSubject.setValue(80);
        simpleSubject.setValue(20);
        simpleSubject.removeObserver(simpleObserver);
        simpleSubject.setValue(1000);

    }
}
