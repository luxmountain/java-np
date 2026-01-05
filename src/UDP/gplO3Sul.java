package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class gplO3Sul {
    private static String handleName(String name) {
        StringBuilder result = new StringBuilder();
        String[] words = name.split(" ");
        result.append(words[words.length - 1]).append(" ");

        for (int i = 1; i < words.length - 1; ++i) {
            result.append(words[i]).append(" ");
        }
        result.append(words[0]);
        return result.toString();
    }

    private static int handleQuantity(int quantity) {
        String result = new StringBuilder(String.valueOf(quantity)).reverse().toString();
        return Integer.parseInt(result);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String qCode = "gplO3Sul";
        String studentCode = "B22DCVT445";
        int port = 2209;
        String host = "203.162.10.109";
        String message = ";" + studentCode + ";" + qCode;

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(host);

            byte[] sendMessage = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length, serverAddress, port);
            socket.send(sendPacket);

            byte[] receiveBuffer = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            byte[] requestIdBytes = new byte[8];
            System.arraycopy(receivePacket.getData(), 0, requestIdBytes, 0, requestIdBytes.length);

            byte[] objectBytes = new byte[receivePacket.getLength() - 8];
            System.arraycopy(receivePacket.getData(), 8, objectBytes, 0, objectBytes.length);

            ByteArrayInputStream bais = new ByteArrayInputStream(objectBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);

            Product product = (Product) ois.readObject();
            System.out.println("Product: " + product);

            product.setName(handleName(product.getName()));
            product.setQuantity(handleQuantity(product.getQuantity()));

            System.out.println("Product After: " + product);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(product);
            oos.flush();

            byte[] productBytes = baos.toByteArray();
            byte[] sendBack = new byte[8 + productBytes.length];
            System.arraycopy(requestIdBytes, 0, sendBack, 0, requestIdBytes.length);
            System.arraycopy(productBytes, 0, sendBack, 8, productBytes.length);

            DatagramPacket sendResultPacket = new DatagramPacket(sendBack, sendBack.length, serverAddress, port);
            socket.send(sendResultPacket);

            socket.close();


        } catch (IOException e) {
        }
    }
}
