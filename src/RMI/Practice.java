package RMI;

import java.io.Serializable;
import java.rmi.Naming;

public class Practice {
    public static void main(String[] args) {
        String studentCode = "B22DCVT445";
        String qCode = "CIkysC4y";
        String url = "rmi://203.162.10.109/RMIObjectService";

        try {
            ObjectService service = (ObjectService) Naming.lookup(url);
            Serializable object = service.requestObject(studentCode, qCode);
            System.out.println("Input: " + object);

            Product product = (Product) object;
            product.setCode(product.getCode().toUpperCase());
            product.setExportPrice(product.getImportPrice() * 1.2);

            service.submitObject(studentCode, qCode, product);
            System.out.println("Output: " + product);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}