import java.util.*;

public class CafeteriaSystem {
    private final Menu menu;
    private final PolicyResolver resolver;
    private final InvoiceService invoiceService;
    

    public CafeteriaSystem(Menu menu, FileStore store, PricingService pricingService, PolicyResolver resolver) {
         this.menu = menu;
         this.resolver = resolver;
         this.invoiceService = new InvoiceService(store, pricingService);
    }

    public void checkout(String customerType, List<OrderLine> lines) {
        TaxPolicy taxPolicy = resolver.resolveTax(customerType);
        DiscountPolicy discountPolicy = resolver.resolveDiscount(customerType);
        invoiceService.generateInvoice(menu, lines, taxPolicy, discountPolicy);             
    }
}
