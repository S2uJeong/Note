package bookStrangeCode;

public class Main {

    static class Money {
        private final int amount;

        public Money(int amount) {
            // amount = 10000;
            this.amount = amount;
        }

        Money add(int other) {
            int added = amount + other;
            return new Money(added);
        }
    }
    public static void main(String[] args) {
        Money money = new Money(200);

        System.out.println(money.amount);
    }
}
