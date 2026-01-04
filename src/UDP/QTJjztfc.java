package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.TreeMap;

public class QTJjztfc {
    public static void main(String[] args) {
        int port = 2207;
        String host = "203.162.10.109";
        String studentCode = "B22DCVT445";
        String qCode = "QTJjztfc";
        String messsage = ";" + studentCode + ";" + qCode;

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(host);

            byte[] sendMessage = messsage.getBytes();
            DatagramPacket sendMessagePacket = new DatagramPacket(sendMessage, sendMessage.length, serverAddress, port);
            socket.send(sendMessagePacket);

            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Response: " + response);

            String[] parts = response.split(";");
            String requestId = parts[0];
            String data = parts[1];

            String[] items = data.split(",");
            TreeMap<Integer, String> map = new TreeMap<>();
            for(String item: items){
                String[] pair = item.split(":");
                map.put(Integer.valueOf(pair[1]), pair[0]);
            }

            StringBuilder result = new StringBuilder();
            for(String value : map.values()){
                result.append(value).append(",");
            }

            result.deleteCharAt(result.length() - 1);

            String resultMessage = requestId + ";" + result.toString();

            System.out.println("Result: " + resultMessage);
            byte[] sendResultMessage = resultMessage.getBytes();
            DatagramPacket sendResultPacket = new DatagramPacket(sendResultMessage, sendResultMessage.length, serverAddress, port);
            socket.send(sendResultPacket);

        } catch (IOException e) {
        }

    }
}
