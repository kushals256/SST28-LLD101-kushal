import java.util.List;

public class InvoiceFormatter {
    // pointless wrapper (smell)
    public static String format(
        String invId, 
        Menu menu, 
        List<OrderLine> lines, 
        double subtotal, 
        double taxPercent, 
        double tax, 
        double discount, 
        double total) {     
            StringBuilder out = new StringBuilder();
            out.append("Invoice# ").append(invId).append("\n");

            for (OrderLine l : lines) {
                MenuItem item = menu.getMenu().get(l.itemId);
                double lineTotal = item.price * l.qty;
                out.append(String.format("- %s x%d = %.2f\n", item.name, l.qty, lineTotal));
            }

            out.append(String.format("Subtotal: %.2f\n", subtotal));
            out.append(String.format("Tax(%.0f%%): %.2f\n", taxPercent, tax));
            out.append(String.format("Discount: -%.2f\n", discount));
            out.append(String.format("TOTAL: %.2f\n", total));

            return out.toString();
    }
}
