import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");
        RuleInput ruleInput = new RuleInput();
        List<EligibilityRule> rules = List.of(
                new CgrRule(ruleInput.minCgr),
                new AttendanceRule(ruleInput.minAttendance),
                new CreditsRule(ruleInput.minCredits)
        );
        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);
        EligibilityEngine engine = new EligibilityEngine(new FakeEligibilityStore(), rules);
        engine.runAndPrint(s);
    }
}
