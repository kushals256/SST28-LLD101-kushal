import java.nio.charset.StandardCharsets;

public class JsonExporter implements Exporter {

    @Override
    public boolean supports(ExportRequest req) {
        return req != null;
    }

    @Override
    public ExportResult export(ExportRequest req) {
        if (!supports(req)) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        String body = req.body == null ? "" : req.body;

        String json = "{"
                + "\"title\":\"" + escape(req.title) + "\","
                + "\"body\":\"" + escape(body) + "\""
                + "}";

        return new ExportResult(
                "application/json",
                json.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\"", "\\\"");
    }
}