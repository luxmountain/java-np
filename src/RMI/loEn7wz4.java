package RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.Map;

public class loEn7wz4 {
    public static void main(String args[]) throws RemoteException, NotBoundException, MalformedURLException {
        String studentCode = "B22DCVT445";
        String qCode = "loEn7wz4";

        String url = "rmi://203.162.10.109/RMICharacterService";
        CharacterService service = (CharacterService) Naming.lookup(url);

        String input = service.requestCharacter(studentCode, qCode);
        System.out.println("Input: " + input);

        Map<Character, Integer> map = new LinkedHashMap<>();

        for (char c : input.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();

        sb.append("{");

        for (Character key : map.keySet()) {
            sb.append("\"").append(key).append("\": ").append(map.get(key)).append(", ");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");

        service.submitCharacter(studentCode, qCode, sb.toString());
        System.out.println("Result: " + sb.toString());
    }
}
