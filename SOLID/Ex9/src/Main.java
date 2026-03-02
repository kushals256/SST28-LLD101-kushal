public class Main {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");
        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");
        CodeGrader grader = new CodeGraderImpl();
        CodeGrader grader2 = new CodeGraderIMpl2();
        Rubric rubric = new Rubric();
        PlagiarismChecker pc = new PlagiarismCheckerImpl();
        ReportWriter writer = new ReportWriterImpl();
        new EvaluationPipeline(rubric, pc, grader2, writer).evaluate(sub);
    }
}
