package RMI;
import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 20151107L;
    private String id;
    private String code;
    private double importPrice;
    private double exportPrice;

    public Product() {}
    public Product(String id, String code, double importPrice, double exportPrice) {
        this.id = id;
        this.code = code;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
    }

    public String getId() { return id; }
    public String getCode() { return code; }
    public double getImportPrice() { return importPrice; }
    public double getExportPrice() { return exportPrice; }

    public void setCode(String code) { this.code = code; }
    public void setImportPrice(double importPrice) { this.importPrice = importPrice; }
    public void setExportPrice(double exportPrice) { this.exportPrice = exportPrice; }

    @Override
    public String toString() {
        return String.format("Product{id='%s', code='%s', importPrice=%.2f, exportPrice=%.2f}",
                id, code, importPrice, exportPrice);
    }
}
