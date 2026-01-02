package ws.np;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ObjeactService {

    public static void main(String[] args) {
        String studentCode = "B22DCVT445";
        String qCode = "68VbbYn5";

        try {
            // ===== CÁCH 1: DÙNG URL WSDL TRỰC TIẾP =====
            URL wsdlURL = new URL(
                    "http://203.162.10.109:8080/JNPWS/ObjectService?wsdl"
            );

            QName SERVICE_NAME = new QName(
                    "http://service/",
                    "ObjectService"
            );

            ObjectService_Service service =
                    new ObjectService_Service(wsdlURL, SERVICE_NAME);

            ObjectService port = service.getObjectServicePort();

            System.out.println("Requesting list of students...");
            List<Student> students = port.requestListStudent(studentCode, qCode);

            System.out.println("Received " + students.size() + " students:");
            for (Student student : students) {
                System.out.println("- " + student.getName() + ": " + student.getScore());
            }

            List<Student> filteredStudents = new ArrayList<>();
            for (Student student : students) {
                float score = student.getScore();
                if (score >= 8.0f || score < 5.0f) {
                    filteredStudents.add(student);
                    System.out.println(
                            "Filtered: " + student.getName()
                                    + " (Score: " + score
                                    + ", Group: " + (score >= 8.0f ? "A" : "D") + ")"
                    );
                }
            }

            System.out.println("\nSubmitting " + filteredStudents.size()
                    + " students (Group A and D)...");

            port.submitListStudent(studentCode, qCode, filteredStudents);

            System.out.println("Submission completed successfully!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
