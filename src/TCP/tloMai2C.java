package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class tloMai2C {
    public static void main(String[] args) throws IOException {
        int port = 2206;
        String sCode = "B22DCVT445";
        String qCode = "tloMai2C";
        String serverAddress = "203.162.10.109";
        String message = sCode + ";" + qCode;

        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(serverAddress, port), 5000);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            
            outputStream.write(message.getBytes());
            outputStream.flush();
            System.out.println("Sent: " + message);

            byte[] buffer = new byte[2048];
            int bytesRead = inputStream.read(buffer);
            String receivedData = new String(buffer, 0, bytesRead);
            System.out.println("Received: " + receivedData);

            String[] numbers = receivedData.split("\\|");
            
            int sum = 0;
            for(String number : numbers){
                sum += Integer.parseInt(number);
            }

            String result = String.valueOf(sum);
            outputStream.write(result.getBytes());
            outputStream.flush();
            System.out.println("Sent: " + result);

        } catch (IOException e){
            System.out.println("Error: " + e);
        }

    }
}
