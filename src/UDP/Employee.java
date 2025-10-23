package UDP;
import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 20261107L;
    private String id;
    private String name;
    private double salary;
    private String hireDate;

    public Employee(String id, String name, double salary, String hireDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // getters và setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public String getHireDate() { return hireDate; }

    public void setName(String name) { this.name = name; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }

    @Override
    public String toString() {
        return String.format("Employee{id='%s', name='%s', salary=%.2f, hireDate='%s'}",
                id, name, salary, hireDate);
    }
}
