package RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HexFormat;

public class wyKxYdSJ {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String studentCode = "B22DCVT445";        
        String qCode = "wyKxYdSJ";

        String url = "rmi://203.162.10.109/RMIByteService";
        ByteService service = (ByteService) Naming.lookup(url);

        byte[] input = service.requestData(studentCode, qCode);
        
        String hex = HexFormat.of().formatHex(input);

        byte[] result = hex.getBytes();
        service.submitData(studentCode, qCode, result);
    }
}
