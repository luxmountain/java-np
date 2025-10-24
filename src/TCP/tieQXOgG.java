package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class tieQXOgG {

    public static void main(String[] args) {
        String serverAddress = "203.162.10.109";
        String qCode = "tieQXOgG";
        String sCode = "B22DCVT445";
        String message = sCode + ";" + qCode;
        int port = 2207;

        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(serverAddress,port), 5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(message);
            dos.flush();
            System.out.println("Sent: " + message);

            int n = dis.readInt();
            System.out.println("Received: " + n);

            String binary = Integer.toBinaryString(n);
            dos.writeUTF(binary);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
