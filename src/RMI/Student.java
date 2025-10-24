package RMI;

import java.io.Serial;
import java.io.Serializable;

public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = 20241130L;

    private String id;
    private String name;
    private int enrollmentYear;
    private String code;

    public Student() {}

    public Student(String id, String name, int enrollmentYear) {
        this.id = id;
        this.name = name;
        this.enrollmentYear = enrollmentYear;
    }

    // Các getter và setter
    public String getId() { return id; }
    public String getName() { return name; }
    public int getEnrollmentYear() { return enrollmentYear; }
    public String getCode() { return code; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEnrollmentYear(int enrollmentYear) { this.enrollmentYear = enrollmentYear; }
    public void setCode(String code) { this.code = code; }

    @Override
    public String toString() {
        return "Student{id='" + id + "', name='" + name + "', year=" + enrollmentYear + ", code='" + code + "'}";
    }
}
