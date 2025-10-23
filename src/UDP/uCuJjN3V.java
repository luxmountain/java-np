package UDP;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class uCuJjN3V {

    private static String capitalizeWords(String name) {
        String[] parts = name.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isEmpty()) {
                sb.append(Character.toUpperCase(p.charAt(0)))
                        .append(p.substring(1)).append(" ");
            }
        }
        return sb.toString().trim();
    }

    private static String convertDate(String date) {
        try {
            java.util.Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return new SimpleDateFormat("dd/MM/yyyy").format(d);
        } catch (Exception e) {
            return date;
        }
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
        int serverPort = 2209;

        String studentCode = "B22DCVT445";
        String qCode = "uCuJjN3V";
        String message = ";" + studentCode + ";" + qCode;

        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);
        System.out.println("Sent: " + message);

        byte[] receiveBuffer = new byte[65535];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        socket.receive(receivePacket);

        byte[] requestIdBytes = new byte[8];
        System.arraycopy(receivePacket.getData(), 0, requestIdBytes, 0, 8);
        String requestId = new String(requestIdBytes).trim();

        byte[] objectBytes = new byte[receivePacket.getLength() - 8];
        System.arraycopy(receivePacket.getData(), 8, objectBytes, 0, objectBytes.length);

        ByteArrayInputStream bais = new ByteArrayInputStream(objectBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Employee emp = (Employee) ois.readObject();

        System.out.println("Received Employee: " + emp);

        emp.setName(capitalizeWords(emp.getName()));
        String yearStr = emp.getHireDate().substring(0, 4);
        int birthYear = Integer.parseInt(yearStr);
        int sumDigits = String.valueOf(birthYear)
                .chars()
                .map(Character::getNumericValue)
                .sum();
        emp.setSalary(emp.getSalary() * (1 + sumDigits / 100.0));

        emp.setHireDate(convertDate(emp.getHireDate()));

        System.out.println("Modified Employee: " + emp);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(emp);
        oos.flush();

        byte[] empBytes = baos.toByteArray();
        byte[] sendBack = new byte[8 + empBytes.length];
        System.arraycopy(requestIdBytes, 0, sendBack, 0, 8);
        System.arraycopy(empBytes, 0, sendBack, 8, empBytes.length);

        DatagramPacket sendBackPacket = new DatagramPacket(sendBack, sendBack.length, serverAddress, serverPort);
        socket.send(sendBackPacket);
        System.out.println("Sent modified Employee back to server.");

        socket.close();
    }
}
