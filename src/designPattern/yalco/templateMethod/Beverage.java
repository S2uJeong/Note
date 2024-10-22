package designPattern.yalco.templateMethod;

/**
 * 추상 클래스는 원하는 메서드들의 구현부를 작성하는 것이 가능하다.
 * 추상 클래스 안에서 final이 달린 메서드는 해당 메서드를 오버라이드할 수 없도록 한다.
 */
abstract class Beverage {
    // Template method : 해당 클래스의 규칙. 구현부가 있어야 하므로 interface가 아닌 추상클래스를 사용하는 것
    final void prepareRecipe() {
        boilWater();
        brew();
        pourIncup();
        addCondiments();
    }

    void boilWater() {
        System.out.println("boiling");
    }

    void pourIncup() {
        System.out.println("pouring");
    }

    abstract void brew();
    abstract void addCondiments();

}
class Tea extends Beverage{

    @Override
    void brew() {
        System.out.println("steeping tea");
    }

    @Override
    void addCondiments() {
        System.out.println("adding lemon");
    }
}
class Coffee extends Beverage {

    @Override
    void brew() {
        System.out.println("dripping coffee through filter");
    }

    @Override
    void addCondiments() {
        System.out.println("adding sugar and milk");
    }
}
