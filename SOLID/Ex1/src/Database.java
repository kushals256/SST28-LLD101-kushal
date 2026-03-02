import java.util.List;

public interface Database {
    void save(StudentRecord record);
    int count();
    List<StudentRecord> all();
}