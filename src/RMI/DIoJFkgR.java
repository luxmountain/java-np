//[Mã câu hỏi (qCode): DIoJFkgR].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
//Giao diện từ xa:
//public interface ByteService extends Remote {
//    public byte[] requestData(String studentCode, String qCode) throws RemoteException;
//    public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
//}
//Trong đó:
//        •	Interface ByteService được viết trong package RMI.
//•	Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là: RMIByteService.
//Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhị phân nhận được từ RMI Server:
//a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server.
//b. Chuyển đổi mảng dữ liệu nhị phân nhận được thành một chuỗi biểu diễn hex. Mỗi byte trong mảng sẽ được chuyển đổi thành hai ký tự hex tương ứng.
//Ví dụ: Nếu dữ liệu nhị phân nhận được là [72, 101, 108, 108, 111], chương trình sẽ chuyển đổi mảng này thành chuỗi hex "48656c6c6f", tương ứng với chuỗi "Hello" trong ASCII.
//c. Triệu gọi phương thức submitData để gửi chuỗi biểu diễn hex đã chuyển đổi thành mảng byte trở lại server.
//d. Kết thúc chương trình client.

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
