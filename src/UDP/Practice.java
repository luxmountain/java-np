package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Practice {
    private static String standardizeName(String name) {
        String[] parts = name.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                result.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(" ");
            }
        }
        return result.toString().trim();
    }

    private static String convertDate(String date) throws ParseException {
        Date temp = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        return new SimpleDateFormat("dd/MM/yyyy").format(temp);
    }

    public static void main(String[] args) {
        String studentCode = "B22DCVT445";
        String qCode = "uCuJjN3V";
        int port = 2209;
        String host = "203.162.10.109";
        String message = ";" + studentCode + ";" + qCode;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(host);

            // a
            byte[] sendMessage = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length, serverAddress, port);
            socket.send(sendPacket);
            System.out.println("Sent: " + message);

            // b
            byte[] receiveBuffer = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            byte[] requestIdBytes = new byte[8];
            System.arraycopy(receivePacket.getData(), 0, requestIdBytes, 0, 8);

            byte[] objectBytes = new byte[receivePacket.getLength() - 8];
            System.arraycopy(receivePacket.getData(), 8, objectBytes, 0, objectBytes.length);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Employee employee = (Employee) objectInputStream.readObject();
            System.out.println("Receive Employee: " + employee);

            employee.setName(standardizeName(employee.getName()));
            String yearStr = employee.getHireDate().substring(0, 4);
            employee.setHireDate(convertDate(employee.getHireDate()));

            int sumDigits = yearStr.chars().map(Character::getNumericValue).sum();
            employee.setSalary(employee.getSalary() * (1 + sumDigits / 100.0));

            System.out.println("Fixed Employee: " + employee);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(employee);
            objectOutputStream.flush();

            byte[] emptyBytes = byteArrayOutputStream.toByteArray();
            byte[] sendBack = new byte[8 + emptyBytes.length];
            System.arraycopy(requestIdBytes, 0, sendBack, 0, 8);
            System.arraycopy(emptyBytes, 0, sendBack, 8, emptyBytes.length);

            DatagramPacket sendBackPacket = new DatagramPacket(sendBack, sendBack.length, serverAddress, port);
            socket.send(sendBackPacket);
            System.out.println("Sent packet");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}