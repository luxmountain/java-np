package RMI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DIoJFkgR {
    public static void main(String[] args) {
        try {
            // --- Thông tin sinh viên và mã câu hỏi ---
            String studentCode = "B22DCVT445";   // thay bằng mã sinh viên của bạn
            String qCode = "DIoJFkgR";

            // --- Kết nối tới RMI Registry ---
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
            ByteService service = (ByteService) registry.lookup("RMIByteService");

            // --- a. Gọi requestData() để nhận dữ liệu nhị phân ---
            byte[] receivedData = service.requestData(studentCode, qCode);
            System.out.println("Đã nhận " + receivedData.length + " byte từ server.");

            // --- b. Chuyển dữ liệu sang chuỗi hex ---
            String hexString = bytesToHex(receivedData);
            System.out.println("Chuỗi hex tương ứng: " + hexString);

            // --- c. Gửi lại dữ liệu hex dưới dạng byte[] ---
            byte[] hexBytes = hexString.getBytes();  // Mỗi ký tự ASCII trong chuỗi hex
            service.submitData(studentCode, qCode, hexBytes);
            System.out.println("Đã gửi chuỗi hex trở lại server.");

            // --- d. Kết thúc chương trình ---
            System.out.println("Client đã hoàn tất.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm hỗ trợ chuyển mảng byte sang chuỗi hex
    private static String bytesToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02x", b)); // %02x: 2 ký tự hex, có 0 ở đầu nếu cần
        }
        return sb.toString();
    }
}
