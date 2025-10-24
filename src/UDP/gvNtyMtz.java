package UDP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class gvNtyMtz {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        String studentCode = "B22DCVT445";
        String qCode = "gvNtyMtz";
        String message = ";" + studentCode + ";" + qCode;
        String host = "203.162.10.109";
        int serverPort = 2207;
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(host);

            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket =  new DatagramPacket(
                    sendData, sendData.length, serverAddress, serverPort
            );
            socket.send(sendPacket);
            System.out.println("Sent: " + message);

            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + receivedMessage);

            String[] parts = receivedMessage.split(";", 4);
            String requestId = parts[0];
            int n = Integer.parseInt(parts[1]);
            int k = Integer.parseInt(parts[2]);
            String[] numberStringList = parts[3].split(",");
            List<Integer> numberList = new ArrayList<>();

            for(String num : numberStringList){
                numberList.add(Integer.parseInt(String.valueOf(num)));
            }

            List<Integer> result = new ArrayList<>();

            for(int i = 0; i < numberList.size() - k + 1; ++i){
                int max = 0;
                for(int j = i; j < i + k ; ++j){
                    if(numberList.get(j) > max)
                        max = numberList.get(j);
                }
                result.add(max);
            }

            StringBuilder sendMessage = new StringBuilder(requestId + ";");
            for(int i = 0; i < result.size(); ++i){
                if(i != 0) sendMessage.append(",");
                sendMessage.append(result.get(i));
            }

            String res = new String(sendMessage);
            byte[] sendResult = res.getBytes();
            DatagramPacket sendResultPacket = new DatagramPacket(sendResult, sendResult.length, serverAddress, serverPort);
            socket.send(sendResultPacket);
            System.out.println("Sent: " + res);

            } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();

        }
    }
}
