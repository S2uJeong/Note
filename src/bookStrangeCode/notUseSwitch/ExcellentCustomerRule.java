package bookStrangeCode.notUseSwitch;

public interface ExcellentCustomerRule {
    // @return 조건을 만족하는 경우 true
    boolean ok(final PurchaseHistory history);
}

// 구매 금액 규칙
class GoldCustomerPurchaseAmountRule implements ExcellentCustomerRule  {
    public boolean ok(final PurchaseHistory history) {
        return 1_000_000 <= history.totalAmount;
    }
}
// 구매 빈도 규칙
class PurchaseFrequencyRule implements ExcellentCustomerRule  {
    public boolean ok(final PurchaseHistory history) {
        return 10<= history.purchaseFrequencyPerMonth;
    }
}
// 반품률 규칙
class ReturnRateRule implements ExcellentCustomerRule  {
    public boolean ok(final PurchaseHistory history) {
        return 0.001 >= history.returnRate;
    }
}
