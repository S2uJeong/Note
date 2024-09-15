package designPattern.observer.simple;

public class SimpleObserver implements Observer{
    private Subject simpleSubject;
    private int value;

    public SimpleObserver(Subject simpleSubject) {
        this.simpleSubject = simpleSubject;
        simpleSubject.registerObserver(this); // 생성 시, subject를 주입하여 옵저버 리스트에 넣어준다. 추후 옵저버 삭제는 실행 코드에서 한다.
    }

    @Override
    public void update(int value) {
        this.value = value;
        System.out.println("SimpleObserver's value : " + String.valueOf(value));
    }
}
