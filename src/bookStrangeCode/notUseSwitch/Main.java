package bookStrangeCode.notUseSwitch;

public class Main {
    public static void main(String[] args) {
        ExcellentCustomerPolicy goldPolicy = new ExcellentCustomerPolicy();
        goldPolicy.add(new GoldCustomerPurchaseAmountRule());
        goldPolicy.add(new PurchaseFrequencyRule());
        goldPolicy.add(new ReturnRateRule());

        goldPolicy.complyWithAll(new PurchaseHistory(1000000,100,0.5));
    }
}
