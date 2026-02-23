public class CgrRule implements EligibilityRule {

    private final double minCgr;

    public CgrRule(double minCgr) {
        this.minCgr = minCgr;
    }

    @Override
    public String evaluate(StudentProfile profile) {
        if (profile.cgr < minCgr) {
            return "CGR below " + minCgr;
        }
        return null;
    }
}