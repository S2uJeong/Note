package bookStrangeCode.notUseSwitch;

import java.util.HashSet;
import java.util.Set;

public class ExcellentCustomerPolicy {
    private final Set<ExcellentCustomerRule> rules; // rule을 세분화 한뒤, 필드를 rule list 만들어 해당하는 것 다 지켜야 한다는 로직 만든게 인상적

    public ExcellentCustomerPolicy() {
        this.rules = new HashSet<>();
    }

    /**
     * 규칙 추가
     * @param rule 규칙
     */
    void add(final ExcellentCustomerRule rule) {
        rules.add(rule);
    }

    /**
     * @param history 구매이력
     * @return 규칙을 모두 만족하는 경우 true
     */
    boolean complyWithAll(final PurchaseHistory history) {
        for (ExcellentCustomerRule each : rules) {
            if (!each.ok(history)) return false;
        }
        return true;
    }
}
