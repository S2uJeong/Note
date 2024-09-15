package designPattern.decorator;

// 추상클래스 말고 인터페이스를 사용해도 되지만, 기존 업체에서 추상클래스를 사용하고 있었다는 가정
public abstract class Beverage {
    String description = "제목 없음";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
    // 서브 클래스에서 구현
}
