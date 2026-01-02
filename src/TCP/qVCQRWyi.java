package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class qVCQRWyi {
    public static String decryptCaesar(String cipherText, int s) {
        StringBuilder result = new StringBuilder();

        for (char cipherChar : cipherText.toCharArray()) {
            if (cipherChar >= 'A' && cipherChar <= 'Z') {
                char decoded = (char) ('A' + (cipherChar - 'A' - s + 26) % 26);
                result.append(decoded);
            } else if (cipherChar >= 'a' && cipherChar <= 'z') {
                char decoded = (char) ('a' + (cipherChar - 'a' - s + 26) % 26);
                result.append(decoded);
            } else {
                result.append(cipherChar);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String studentCode = "B22DCVT445";
        String qCode = "qVCQRWyi";
        String message = studentCode + ";" + qCode;
        String host = "203.162.10.109";
        int serverPort = 2207;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, serverPort), 5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(message);
            dos.flush();
            System.out.println("Sent: " + message);

            String cipherText = dis.readUTF();
            int s = dis.readInt();

            System.out.println("Received text: " + cipherText);
            System.out.println("Received s: " + s);

            String result = decryptCaesar(cipherText, s);
            dos.writeUTF(result);
            dos.flush();
            System.out.println("Sent: " + result);
            
        } catch (Exception e) {
        }
    }
}
