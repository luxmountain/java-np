package RMI;

import java.rmi.*;
import java.rmi.registry.*;
import java.io.Serializable;

public class CIkysC4y {
    public static void main(String[] args) {
        String studentCode = "B22DCVT445";
        String qAlias = "CIkysC4y";

        try {
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);

            ObjectService service = (ObjectService) registry.lookup("RMIObjectService");
            System.out.println("Connected to RMIObjectService.");

            Serializable obj = service.requestObject(studentCode, qAlias);
            if (!(obj instanceof Product)) {
                System.err.println("Received object is not a Product!");
                return;
            }

            Product product = (Product) obj;
            System.out.println("Received from server: " + product);

            product.setCode(product.getCode().toUpperCase());
            product.setExportPrice(product.getImportPrice() * 1.2);

            System.out.println("Normalized product: " + product);

            service.submitObject(studentCode, qAlias, product);
            System.out.println("Submitted normalized product back to server.");

            System.out.println("Client finished successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
