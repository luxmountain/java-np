package TCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class z7h0mWR1 {

    // ✅ Hàm đảo vị trí từ đầu và từ cuối
    private static String fixName(String name) {
        String[] words = name.trim().split("\\s+");
        if (words.length < 2) return name;
        // Đảo vị trí từ đầu ↔ cuối
        String temp = words[0];
        words[0] = words[words.length - 1];
        words[words.length - 1] = temp;
        return String.join(" ", words);
    }

    // ✅ Hàm đảo ngược số lượng (ví dụ: 1899 -> 9981)
    private static int fixQuantity(int quantity) {
        String reversed = new StringBuilder(String.valueOf(quantity)).reverse().toString();
        return Integer.parseInt(reversed);
    }

    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2209;
        String studentCode = "B22DCVT445";
        String qCode = "z7h0mWR1";

        try (Socket socket = new Socket()) {
            // ✅ Timeout 5s
            socket.connect(new InetSocketAddress(host, port), 5000);

            // Gửi & nhận qua luồng đối tượng
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // (1) Gửi chuỗi "studentCode;qCode"
            String msg = studentCode + ";" + qCode;
            oos.writeObject(msg);
            oos.flush();
            System.out.println("Sent request: " + msg);

            // (2) Nhận đối tượng Laptop
            Object obj = ois.readObject();
            if (!(obj instanceof Laptop)) {
                System.err.println("Received unexpected object type!");
                return;
            }

            Laptop laptop = (Laptop) obj;
            System.out.println("Received from server: " + laptop);

            // (3) Sửa thông tin sai
            String fixedName = fixName(laptop.getName());
            int fixedQuantity = fixQuantity(laptop.getQuantity());
            laptop.setName(fixedName);
            laptop.setQuantity(fixedQuantity);

            System.out.println("Fixed Laptop: " + laptop);

            // (4) Gửi lại đối tượng Laptop đã sửa
            oos.writeObject(laptop);
            oos.flush();
            System.out.println("Sent fixed laptop back to server.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
