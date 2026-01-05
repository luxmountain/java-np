package RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class _3BVRLq61 {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String studentCode = "B22DCVT445";
        String qCode = "3BVRLq61";

        String url = "rmi://203.162.10.109/RMIDataService";
        DataService service = (DataService) Naming.lookup(url);

        Object obj = service.requestData(studentCode, qCode);
        int amount = (Integer) obj;

        List<Integer> usedCoins = new ArrayList<>();

        int[] coins = { 10, 5, 2, 1 };

        int remainning = amount;

        for (int coin : coins) {
            while (remainning >= coin) {
                remainning -= coin;
                usedCoins.add(coin);
            }
        }

        String result;
        if (remainning != 0)
            result = "-1";
        else {
            StringBuilder sb = new StringBuilder();
            sb.append(usedCoins.size()).append("; ");

            for (Integer usedCoin : usedCoins) {
                sb.append(usedCoin).append(",");
            }

            sb.deleteCharAt(sb.length() - 1);
            result = sb.toString();
        }

        service.submitData(studentCode, qCode, result);
        System.out.println("Submitted result: " + result);
    }
}
