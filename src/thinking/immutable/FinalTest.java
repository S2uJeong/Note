package thinking.immutable;

import java.util.Objects;

public class FinalTest {
    record CarRecord (String name, int maxSpeed) {
        void run() {
            System.out.println(this.name + "가 달립니다. 최대속도는" + this.maxSpeed + " 입니다.");
        }

    }
    static class Car {
         final String name;
         final int maxSpeed;

        public Car(String name, int maxSpeed) {
            this.name = name;
            this.maxSpeed = maxSpeed;
        }

        void run() {
            System.out.println(this.name + "가 달립니다. 최대속도는" + this.maxSpeed + " 입니다.");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Car car = (Car) o;
            return Objects.equals(name, car.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
    public static void main(String[] args) {
        Car car = new Car("수정카", 200);
        car.run();

        Car car2 = new Car("인정카", 20);
        car2.run();

        Car car3 = new Car("수정카", 20);
        car3.run();

        // 동일성 검사
        if (car == car3) System.out.println("두 차는 동일합니다.");

        // 동등성 검사
        if (car.equals(car3)) System.out.println("동등성 비교가 잘 되고 있습니다.");
        if (car.equals(car2)) System.out.println("두 차는 동등합니다.");

    }
}
