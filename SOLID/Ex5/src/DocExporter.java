public class DocExporter implements Exporter {
    @Override
    public boolean supports(ExportRequest req) {
        return req != null && req.body != null && req.body.length() <= 100;
    }

    @Override
    public ExportResult export(ExportRequest req) {
        if (!supports(req)) {
            throw new IllegalArgumentException("Unsupported export request");
        }
        
        return new ExportResult("application/msword", req.body.getBytes());
    }
}
