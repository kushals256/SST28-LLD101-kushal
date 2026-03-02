import java.nio.charset.StandardCharsets;

public class PdfExporter implements Exporter {

    @Override
    public boolean supports(ExportRequest req) {
        return req.body == null || req.body.length() <= 20;
    }

    @Override
    public ExportResult export(ExportRequest req) {
        if (!supports(req)) {
            throw new IllegalArgumentException("PDF cannot handle content > 20 chars");
        }

        String body = req.body == null ? "" : req.body;

        String pdf = "PDF Document\n"
                + req.title + "\n"
                + body;

        return new ExportResult(
                "application/pdf",
                pdf.getBytes(StandardCharsets.UTF_8)
        );
    }
}