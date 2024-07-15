package designPattern.observer.simple;

import java.util.ArrayList;

public class SimpleSubject implements Subject{
    private ArrayList<Observer> observers;
    private int value = 0; // 값 초기화

    // 생성자에는 observer 리스트만 만들어 준다.
    public SimpleSubject() {
        observers = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer ob) {
        observers.add(ob);
    }

    @Override
    public void removeObserver(Observer ob) {
        observers.remove(ob);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: observers) {
            observer.update(value);
        }
    }

    // setter를 두고, setter 메서드로 실행중에 변경이 일어나면, 변경을 옵저버에서 퍼뜨리는 메서드를 실행시켜 준다.
    public void setValue(int value) {
        this.value = value;
        notifyObservers();
    }
}
