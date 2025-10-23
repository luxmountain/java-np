package RMI;

import RMI.ByteService;
import java.rmi.Naming;

public class _2cLgxN2i {
    public static void main(String[] args) {
        try {
            // a. Thông tin sinh viên và mã câu hỏi
            String studentCode = "B22DCVT445";  // 🔹 Thay mã SV của bạn
            String qCode = "2cLgxN2i";          // 🔹 Mã câu hỏi

            // Kết nối tới RMI Server (mặc định port 1099)
            String url = "rmi://203.162.10.109/RMIByteService";
            ByteService service = (ByteService) Naming.lookup(url);

            // a. Nhận dữ liệu nhị phân từ server
            byte[] data = service.requestData(studentCode, qCode);
            System.out.println("Đã nhận dữ liệu từ server (dạng chuỗi): " + new String(data, "ASCII"));

            // b. Mã hóa Caesar: độ dịch = số phần tử trong mảng
            int shift = data.length;
            byte[] encrypted = caesarEncrypt(data, shift);
            System.out.println("Dữ liệu sau mã hóa: " + new String(encrypted, "ASCII"));

            // c. Gửi dữ liệu đã mã hóa trở lại server
            service.submitData(studentCode, qCode, encrypted);
            System.out.println("Đã gửi dữ liệu mã hóa về server.");

            // d. Kết thúc chương trình
            System.out.println("Chương trình client kết thúc.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm mã hóa Caesar cho mảng byte ASCII
    private static byte[] caesarEncrypt(byte[] data, int shift) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            // Dịch chuyển mỗi byte đi shift bước trong bảng mã ASCII
            result[i] = (byte) (data[i] + shift);
        }
        return result;
    }
}
