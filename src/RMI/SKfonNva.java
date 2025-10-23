package RMI;

import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import java.util.stream.Collectors;

public class SKfonNva {
    private static String computeCoins(int amount) {
        int[] coins = {10, 5, 2, 1};
        List<Integer> used = new ArrayList<>();
        int count = 0;

        for (int c : coins) {
            if (amount <= 0) break;
            int k = amount / c;
            for (int i = 0; i < k; i++) used.add(c);
            count += k;
            amount -= k * c;
        }

        if (amount != 0) {
            return "-1;";
        }

        String coinList = used.isEmpty() ? "" :
                used.stream().map(Object::toString).collect(Collectors.joining(","));
        return count + ";" + coinList;
    }

    public static void main(String[] args) {
        String studentCode = "B22DCVT445";
        String qCode = "SKfonNva";

        try {
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);

            DataService service = (DataService) registry.lookup("RMIDataService");
            System.out.println("Connected to RMIDataService.");

            Object obj = service.requestData(studentCode, qCode);
            if (!(obj instanceof Integer)) {
                System.err.println("Received data is not an integer!");
                return;
            }

            int amount = (Integer) obj;
            System.out.println("Received amount from server: " + amount);

            String result = computeCoins(amount);
            System.out.println("Computed result: " + result);

            service.submitData(studentCode, qCode, result);
            System.out.println("Submitted result back to server.");

            System.out.println("Client finished successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
