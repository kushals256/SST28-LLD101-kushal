public class Main {
    public static void main(String[] args) {
        System.out.println("=== Student Onboarding ===");
        Database db = new FakeDb();
        StudentParser parser = new StudentParser();
        ProgramList programList = new ProgramList();
        programList.add("CSE");
        StudentValidator validator = new StudentValidator(programList);
        OnboardingService svc = new OnboardingService(db, parser, validator);

        String raw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        svc.registerFromRawInput(raw);

        String raw2 = "name=Vikram;email=vikram@sst.edu;phone=9876543211;program=IT";
        svc.registerFromRawInput(raw2);
    }
}  