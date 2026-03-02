public interface Exporter {
    boolean supports(ExportRequest req);
    ExportResult export(ExportRequest req);
}