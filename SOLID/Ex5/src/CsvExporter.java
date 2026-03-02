import java.nio.charset.StandardCharsets;

public class CsvExporter implements Exporter {

    @Override
    public boolean supports(ExportRequest req) {
        return true;
    }

    @Override
    public ExportResult export(ExportRequest req) {
        if (!supports(req)) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        String body = req.body == null ? "" : req.body;

        String csv = "title,body\n"
                + escape(req.title) + ","
                + escape(body);

        return new ExportResult(
                "text/csv",
                csv.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String escape(String value) {
        if (value == null) return "";
        return "\"" + value + "\"";
    }
}