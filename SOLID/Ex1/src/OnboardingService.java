import java.util.*;

public class OnboardingService {
    private final Database db;
    private final StudentParser parser;
    private final StudentValidator validator;

    public OnboardingService(Database db, StudentParser parser, StudentValidator validator) {
        this.db = db;
        this.parser = parser;
        this.validator = validator;
    }

    public void registerFromRawInput(String raw) {

        ConsoleReporter.printInput(raw);

        ParsedStudent parsed = parser.parse(raw);

        List<String> errors = validator.validate(parsed);
        if (!errors.isEmpty()) {
            ConsoleReporter.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(db.count());

        StudentRecord record = new StudentRecord(
                id,
                parsed.getName(),
                parsed.getEmail(),
                parsed.getPhone(),
                parsed.getProgram()
        );

        db.save(record);

        ConsoleReporter.printSuccess(record, db.count());
        ConsoleReporter.printDBDump(db);
    }
}