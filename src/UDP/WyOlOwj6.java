package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class WyOlOwj6 {
    public static void main(String[] args) {
        String studentCode = "B22DCVT445";
        String qCode = "WyOlOwj6";
        String message = ";" + studentCode + ";" + qCode;
        String host = "203.162.10.109";
        int port = 2207;

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(host);

            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
            socket.send(sendPacket);
            System.out.println("Sent: " + message);

            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Response: " + response);

            String[] parts = response.split(";");
            String requestId = parts[0];
            String data = parts[1];

            System.out.println("Data: " + data);
            String[] words = data.split(" ");
            StringBuilder result = new StringBuilder();
            for(String word : words){
                result.append(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ");
            }

            String resMesssage = requestId + ";" + result.toString();
            byte[] sendResult = resMesssage.getBytes();
            DatagramPacket sendResultPacket = new DatagramPacket(sendResult, sendResult.length, serverAddress, port);
            socket.send(sendResultPacket);
            System.out.println("Sent: " + message);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
