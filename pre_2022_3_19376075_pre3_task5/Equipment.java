import java.math.BigInteger;

public class Equipment implements ValueBody {
    private int id;
    private String name;
    private BigInteger price = new BigInteger("0");

    //来自 ValueBody
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigInteger getPrice() {
        return this.price;
    }

    public void use(Adventurer user) {

    }
}
