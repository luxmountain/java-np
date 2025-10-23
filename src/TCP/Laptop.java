package TCP;
import java.io.Serializable;

public class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711L;

    private int id;
    private String code;
    private String name;
    private int quantity;

    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }

    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return String.format("Laptop{id=%d, code='%s', name='%s', quantity=%d}",
                id, code, name, quantity);
    }
}
