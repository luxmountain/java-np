package RMI;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class uCuJjN3V {
    public static void main(String[] args) throws UnknownHostException {
        String qCode = "uCuJjN3V";
        String sCode = "B22DCVT445";
        String message = ";" + sCode + ";" + qCode;
        int port = 2209;
        InetAddress serverAddress = InetAddress.getByName("203.162.10.109");

        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(
                    sendData, sendData.length, serverAddress, port
            );
            socket.send(sendPacket);
            System.out.println("Sent: " + message);

            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String receivedMessage = new String(receivePacket.getData(), 0 , receivePacket.getLength());
            System.out.println("Received: " + receivedMessage);



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
