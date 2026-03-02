import java.util.List;

public class InvoiceService {
    private int invoiceSeq = 1000;
    private final FileStore store;
    private final PricingService pricingService;

    public InvoiceService(FileStore store, PricingService pricingService) {
        this.store = store;
        this.pricingService = pricingService;
    }

    public void generateInvoice(Menu menu,
                              List<OrderLine> lines,
                              TaxPolicy taxPolicy,
                              DiscountPolicy discountPolicy) {

        String invId = "INV-" + (++invoiceSeq);

        double subtotal = pricingService.calculateSubtotal(menu, lines);

        double tax = taxPolicy.calculateTax(subtotal);

        double discount = discountPolicy.calculate(subtotal, lines.size());

        double total = subtotal + tax - discount;

        String invoice = InvoiceFormatter.format(
                invId,
                menu,
                lines,
                subtotal,
                taxPolicy.getPercent(),
                tax,
                discount,
                total
        );

        store.save(invId, invoice);
        System.out.print(invoice);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")\n");
    }
}
