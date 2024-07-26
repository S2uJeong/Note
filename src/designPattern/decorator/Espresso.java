package designPattern.decorator;

public class Espresso extends Beverage{
    public Espresso() {
        description = "에스프레소";  // Beverage로 부터 상속받은 변수
    }

    @Override
    public double cost() {
        return 1.99;  // 오직 에스프레소에 관한 가격만 리턴
    }
}
