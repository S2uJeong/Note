package designPattern.decorator;

public abstract class CondimentDecorator extends Beverage{
    Beverage beverage;  // 각 데코레이터가 감쌀 음료를 나타내는 객체. 데코레이터에서 어떤 음료든 감쌀 수 있도록 Beverage 슈퍼 클래스 유형 사용
    public abstract String getDescription();
    @Override
    public double cost() {
        return 0;
    }
}
