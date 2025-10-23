//[Mã câu hỏi (qCode): BBylrcvA].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
//Giao diện từ xa:
//public interface CharacterService extends Remote {
//    public String requestCharacter(String studentCode, String qCode) throws RemoteException;
//    public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
//}
//Trong đó:
//        •	Interface CharacterService được viết trong package RMI.
//•	Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
//Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
//a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Từ khóa;Chuỗi đầu vào"
//b. Thực hiện thao tác mã hóa Vigenère cho chuỗi nhận được. Biết rằng, mã hóa Vigenère thực hiện mã hóa mỗi ký tự trong chuỗi đầu vào được dịch đi một khoảng bằng với vị trí tương ứng của ký tự trong từ khóa. (Từ khóa được lặp lại để khớp với độ dài của chuỗi)
//Ví dụ: chuỗi ban đầu "PTIT;HELLO" -> từ khóa "PTIT" và chuỗi mã hóa là: "WXTED"
//c. Triệu gọi phương thức submitCharacter để gửi chuỗi đã được mã hóa trở lại server.
//d. Kết thúc chương trình client.

package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import RMI.CharacterService;

public class BBylrcvA {

    // --- Hàm mã hóa Vigenère ---
    private static String vigenereEncrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        int keyLen = key.length();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (Character.isLetter(c)) {
                char keyChar = key.charAt(i % keyLen);
                int shift = keyChar - 'A'; // khoảng dịch dựa trên ký tự từ khóa

                char encryptedChar = (char) ((c - 'A' + shift) % 26 + 'A');
                result.append(encryptedChar);
            } else {
                // nếu có ký tự không phải chữ, giữ nguyên
                result.append(c);
            }
        }
        return result.toString().toLowerCase();
    }

    public static void main(String[] args) {
        try {
            String host = "203.162.10.109";
            int port = 1099;
            String serviceName = "RMICharacterService";

            String studentCode = "B22DCVT445";
            String qCode = "BBylrcvA";

            Registry registry = LocateRegistry.getRegistry(host,port);
            CharacterService stub = (CharacterService) registry.lookup(serviceName);
            System.out.println("Ket noi thanh cong");

            String data = stub.requestCharacter(studentCode,qCode);
            System.out.println(data);

            String[] tach = data.split(";");
            String keyword = tach[0].toUpperCase();
            String input = tach[1].toUpperCase();

            String result = vigenereEncrypt(input,keyword);
            System.out.println(result);

            stub.submitCharacter(studentCode,qCode,result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}