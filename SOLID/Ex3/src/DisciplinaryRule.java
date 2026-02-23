public class DisciplinaryRule implements EligibilityRule {

    @Override
    public String evaluate(StudentProfile profile) {
        if (profile.disciplinaryFlag != LegacyFlags.NONE) {
            return "disciplinary flag present";
        }
        return null;
    }
}