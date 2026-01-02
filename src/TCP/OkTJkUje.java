package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class OkTJkUje {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        String studentCode = "B22DCVT445";
        String qCode = "OkTJkUje";
        String message = studentCode + ";" + qCode;
        String host = "203.162.10.109";
        int serverPort = 2206;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, serverPort), 5000);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            outputStream.write(message.getBytes());
            outputStream.flush();
            System.out.println("Sent: " + message);

            byte[] buffer = new byte[2048];
            int bytesRead = inputStream.read(buffer);
            String receivedData = new String(buffer, 0, bytesRead);
            System.out.println("Received: " + receivedData);

            int number = Integer.parseInt(receivedData.trim());
            List<Integer> sequence = new ArrayList<>();
            sequence.add(number);

            while (number != 1) {
                if (number % 2 == 0) number /= 2;
                else number = 3 * number + 1;
                sequence.add(number);
            }

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < sequence.size(); ++i) {
                if (i != 0) result.append(" ");
                result.append(String.valueOf(sequence.get(i)));
            }

            result.append("; ").append(sequence.size());
            outputStream.write(result.toString().getBytes());
            outputStream.flush();
            System.out.println("Sent: " + result);

            } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
