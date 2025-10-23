package RMIClient;

import RMI.DataService;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

public class CBKWkBOe {
    public static void main(String[] args) {
        try {
            // Thông tin sinh viên và mã câu hỏi
            String studentCode = "B22DCVT445";   // Thay bằng mã sinh viên của bạn
            String qCode = "CBKWkBOe";           // Mã câu hỏi

            // Kết nối tới RMI Registry trên máy chủ (mặc định port 1099)
            String url = "rmi://203.162.10.109/RMIDataService";
            DataService service = (DataService) Naming.lookup(url);

            // a. Nhận dữ liệu N từ server
            Object obj = service.requestData(studentCode, qCode);
            if (!(obj instanceof Number)) {
                System.out.println("Dữ liệu nhận không phải là số nguyên!");
                return;
            }

            int N = ((Number) obj).intValue();
            System.out.println("Số N nhận được từ server: " + N);

            // b. Phân tích N thành các thừa số nguyên tố
            List<Integer> factors = primeFactorization(N);
            System.out.println("Các thừa số nguyên tố: " + factors);

            // c. Gửi danh sách các thừa số nguyên tố về server
            service.submitData(studentCode, qCode, factors);
            System.out.println("Đã gửi danh sách thừa số nguyên tố về server.");

            // d. Kết thúc chương trình
            System.out.println("Chương trình client kết thúc.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm phân tích số N thành thừa số nguyên tố (dùng int)
    private static List<Integer> primeFactorization(int n) {
        List<Integer> factors = new ArrayList<>();

        // Chia cho 2 trước
        while (n % 2 == 0) {
            factors.add(2);
            n /= 2;
        }

        // Chia cho các số lẻ từ 3 trở đi
        for (int i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }

        // Nếu n còn lại > 2 thì chính là một số nguyên tố
        if (n > 2) {
            factors.add(n);
        }

        return factors;
    }
}
