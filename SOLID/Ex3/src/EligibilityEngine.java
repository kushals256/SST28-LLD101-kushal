import java.util.*;

public class EligibilityEngine {
    private final FakeEligibilityStore store;
    private final List<EligibilityRule> rules;

    public EligibilityEngine(FakeEligibilityStore store, List<EligibilityRule> rules) { 
        this.store = store; 
        this.rules = rules;
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s); // giant conditional inside
        p.print(s, r);
        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();

        for (EligibilityRule rule : rules) {
            String failure = rule.evaluate(s);
            if (failure != null) {
                reasons.add(failure);
            }
        }

        boolean eligible = reasons.isEmpty();

        EligibilityEngineResult result = new EligibilityEngineResult(
                eligible ? "ELIGIBLE" : "NOT_ELIGIBLE", reasons);

        return result;
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;
    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}
