package bookStrangeCode.notUseSwitch;

public class PurchaseHistory {

    final int totalAmount;
    final int purchaseFrequencyPerMonth;
    final double returnRate;

    public PurchaseHistory(int totalAmount, int purchaseFrequencyPerMonth, double returnRate) {
        this.totalAmount = totalAmount;
        this.purchaseFrequencyPerMonth = purchaseFrequencyPerMonth;
        this.returnRate = returnRate;
    }
}
