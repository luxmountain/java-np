package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ZoiJLJAE {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            // Create UDP socket
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
            int serverPort = 2208;
            
            // a. Send student code and question code
            String studentCode = "B22DCVT445";
            String qCode = "ZoiJLJAE";
            String message = ";" + studentCode + ";" + qCode;
            
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
                                                           serverAddress, serverPort);
            socket.send(sendPacket);
            System.out.println("Sent: " + message);
            
            // b. Receive response from server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + response);
            
            // Parse response: requestId;b1,b2
            String[] parts = response.split(";");
            String requestId = parts[0];
            String[] binaryNumbers = parts[1].split(",");
            String b1 = binaryNumbers[0];
            String b2 = binaryNumbers[1];
            
            System.out.println("Request ID: " + requestId);
            System.out.println("Binary 1: " + b1);
            System.out.println("Binary 2: " + b2);
            
            // c. Calculate sum of two binary numbers and convert to decimal
            int decimal1 = Integer.parseInt(b1, 2);
            int decimal2 = Integer.parseInt(b2, 2);
            int sum = decimal1 + decimal2;
            
            System.out.println("Decimal 1: " + decimal1);
            System.out.println("Decimal 2: " + decimal2);
            System.out.println("Sum: " + sum);
            
            // Send result back to server
            String result = requestId + ";" + sum;
            byte[] resultData = result.getBytes();
            DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length,
                                                             serverAddress, serverPort);
            socket.send(resultPacket);
            System.out.println("Sent result: " + result);
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        } finally {
            // d. Close socket
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Socket closed");
            }
        }
    }
}
