import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Cafeteria Billing ===");

        Menu menu = new Menu();
        menu.addMenuItem(new MenuItem("M1", "Veg Thali", 80.00));
        menu.addMenuItem(new MenuItem("C1", "Coffee", 30.00));
        menu.addMenuItem(new MenuItem("S1", "Sandwich", 60.00));
        FileStore store = new FileStore();
        PricingService pricingService = new PricingService();

        Map<String, TaxPolicy> taxMap = new HashMap<>();
        taxMap.put("student", new StudentTaxPolicy());
        taxMap.put("staff", new StaffTaxPolicy());

        Map<String, DiscountPolicy> discountMap = new HashMap<>();
        discountMap.put("student", new StudentDiscountPolicy());
        discountMap.put("staff", new StaffDiscountPolicy());

        PolicyResolver resolver = new PolicyResolver(taxMap, discountMap);
        CafeteriaSystem sys = new CafeteriaSystem(menu, store, pricingService, resolver);
        
        List<OrderLine> order = List.of(
                new OrderLine("M1", 2),
                new OrderLine("C1", 1)
        );

        sys.checkout("student", order);
        sys.checkout("staff", order);
    }
}
