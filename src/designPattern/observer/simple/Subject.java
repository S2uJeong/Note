package designPattern.observer.simple;

public interface Subject {
     void registerObserver(Observer ob);
     void removeObserver(Observer ob);
     void notifyObservers();

}
