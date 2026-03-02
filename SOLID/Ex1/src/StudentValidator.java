import java.util.ArrayList;
import java.util.List;

public class StudentValidator {
    ProgramList programList;
    public StudentValidator(ProgramList programList) { this.programList = programList; }
    public List<String> validate(ParsedStudent parsed) {
        List<String> errors = new ArrayList<>();
        String name = parsed.getName();
        String email = parsed.getEmail();
        String phone = parsed.getPhone();
        String program = parsed.getProgram();

        if (name.isBlank()) errors.add("Name is required.");

        if (email.isBlank()) errors.add("Email is required.");
        else if (!email.contains("@")) errors.add("Email is invalid.");

        if (phone.isBlank()) errors.add("Phone is required.");
        else if (!phone.matches("\\d{10}")) errors.add("Phone is invalid.");

        if (program.isBlank()) errors.add("Program is required.");
        else if (!programList.contains(program)) errors.add("Program is not supported.");

        return errors;
    }
}