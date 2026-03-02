public class EvaluationPipeline {
    private final Rubric rubric;
    private final PlagiarismChecker pc;
    private final CodeGrader grader;
    private final ReportWriter writer;

    public EvaluationPipeline(Rubric rubric, PlagiarismChecker pc, CodeGrader grader, ReportWriter writer) {
        this.rubric = rubric;
        this.pc = pc;
        this.grader = grader;
        this.writer = writer;
    }

    public void evaluate(Submission sub) {
        int plag = pc.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = grader.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = writer.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
