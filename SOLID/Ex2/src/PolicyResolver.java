import java.util.Map;

public class PolicyResolver {

    private final Map<String, TaxPolicy> taxPolicies;
    private final Map<String, DiscountPolicy> discountPolicies;

    public PolicyResolver(Map<String, TaxPolicy> taxPolicies,
                          Map<String, DiscountPolicy> discountPolicies) {
        this.taxPolicies = taxPolicies;
        this.discountPolicies = discountPolicies;
    }

    public TaxPolicy resolveTax(String customerType) {
        return taxPolicies.getOrDefault(
                customerType.toLowerCase(),
                new DefaultTaxPolicy()
        );
    }

    public DiscountPolicy resolveDiscount(String customerType) {
        return discountPolicies.getOrDefault(
                customerType.toLowerCase(),
                new NoDiscountPolicy()
        );
    }
}