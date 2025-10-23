package RMIClient;

import RMI.CharacterService;

import java.rmi.Naming;

public class BBylrcvA {
    public static void main(String[] args) {
        try {
            // Thông tin sinh viên và mã câu hỏi
            String studentCode = "B22DCVT445";   // Thay mã sinh viên của bạn
            String qCode = "BBylrcvA";          // Mã câu hỏi

            // Kết nối tới RMI Registry (mặc định chạy ở localhost, cổng 1099)
            String url = "rmi://203.162.10.109/RMICharacterService";
            CharacterService service = (CharacterService) Naming.lookup(url);

            // a. Gọi requestCharacter để nhận chuỗi
            String received = service.requestCharacter(studentCode, qCode);
            System.out.println("Chuỗi nhận được từ server: " + received);

            // b. Phân tích chuỗi theo định dạng "Từ khóa;Chuỗi đầu vào"
            String[] parts = received.split(";");
            if (parts.length != 2) {
                System.out.println("Định dạng chuỗi không hợp lệ!");
                return;
            }

            String keyword = parts[0].toUpperCase();
            String inputString = parts[1].toUpperCase();

            // Mã hóa Vigenère
            String encrypted = vigenereEncrypt(inputString, keyword);
            System.out.println("Chuỗi sau mã hóa: " + encrypted);

            // c. Gửi chuỗi mã hóa trở lại server
            service.submitCharacter(studentCode, qCode, encrypted);
            System.out.println("Đã gửi kết quả mã hóa về server.");

            // d. Kết thúc chương trình
            System.out.println("Chương trình client kết thúc.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm mã hóa Vigenère
    private static String vigenereEncrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        int keyLen = key.length();

        for (int i = 0; i < text.length(); i++) {
            char p = text.charAt(i);
            if (Character.isLetter(p)) {
                char k = key.charAt(i % keyLen);
                // Giả sử toàn bộ là chữ hoa A-Z
                char c = (char) ((p - 'A' + (k - 'A')) % 26 + 'A');
                result.append(c);
            } else {
                // Giữ nguyên ký tự không phải chữ
                result.append(p);
            }
        }
        return result.toString();
    }
}
