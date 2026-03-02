import java.util.List;

public class ConsoleReporter  {

    public static void printInput(String raw) {
        System.out.println("INPUT: " + raw);
    }

    public static void printSuccess(StudentRecord rec, int total) {
        System.out.println("OK: created student " + rec.getId());
        System.out.println("Saved. Total students: " + total);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }

    public static void printDBDump(Database db) {
        System.out.println();
        System.out.println("-- DB DUMP --");
        System.out.print(TextTable.render3(db));
    }

    public static void printErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        
        for (String e : errors) System.out.println("- " + e);
    }
}