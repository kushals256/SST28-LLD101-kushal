public class CreditsRule implements EligibilityRule {

    private final int minCredits;

    public CreditsRule(int minCredits) {
        this.minCredits = minCredits;
    }

    @Override
    public String evaluate(StudentProfile profile) {
        if (profile.earnedCredits < minCredits) {
            return "earned credits below " + minCredits;
        }
        return null;
    }
}