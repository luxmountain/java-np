package RMI;

import java.rmi.Naming;

public class Pzwphlxx {
    public static void main(String[] args) {
        try {
            // Thông tin sinh viên và mã câu hỏi
            String studentCode = "B22DCVT445";  // Thay bằng mã sinh viên của bạn
            String qCode = "Pzwphlxx";          // Mã câu hỏi

            // Kết nối tới RMI Server (mặc định cổng 1099)
            String url = "rmi://203.162.10.109/RMIObjectService";
            ObjectService service = (ObjectService) Naming.lookup(url);

            // a. Nhận đối tượng Student từ server
            Object obj = service.requestObject(studentCode, qCode);
            if (!(obj instanceof Student)) {
                System.out.println("Dữ liệu nhận không phải là đối tượng Student!");
                return;
            }

            Student student = (Student) obj;
            System.out.println("Đối tượng Student nhận được:");
            System.out.println(" - ID: " + student.getId());
            System.out.println(" - Name: " + student.getName());
            System.out.println(" - Enrollment Year: " + student.getEnrollmentYear());

            // b. Chuẩn hóa tên
            String normalizedName = normalizeName(student.getName());

            // c. Tạo mã code theo quy tắc
            String code = generateStudentCode(normalizedName, student.getEnrollmentYear());

            // Cập nhật lại đối tượng
            student.setName(normalizedName);
            student.setCode(code);

            System.out.println("Tên sau khi chuẩn hóa: " + normalizedName);
            System.out.println("Mã code sinh viên: " + code);

            // d. Gửi lại đối tượng Student về server
            service.submitObject(studentCode, qCode, student);
            System.out.println("Đã gửi đối tượng Student đã xử lý về server.");

            // e. Kết thúc chương trình
            System.out.println("Chương trình client kết thúc.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🧠 Hàm chuẩn hóa tên
    private static String normalizeName(String name) {
        name = name.trim().toLowerCase();
        String[] parts = name.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String part : parts) {
            if (part.isEmpty()) continue;
            sb.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1))
                    .append(" ");
        }

        return sb.toString().trim();
    }

    // 🧩 Hàm tạo mã code
    private static String generateStudentCode(String name, int year) {
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 0) return "B" + year;

        String firstName = parts[parts.length - 1].toUpperCase(); // TÊN (in hoa)
        StringBuilder initials = new StringBuilder();

        // Lấy chữ cái đầu của Họ và Họ lót
        for (int i = 0; i < parts.length - 1; i++) {
            initials.append(Character.toUpperCase(parts[i].charAt(0)));
        }

        String yearSuffix = String.valueOf(year).substring(2); // Hai chữ số cuối của năm
        return "B" + yearSuffix + firstName + "_" + initials;
    }
}
