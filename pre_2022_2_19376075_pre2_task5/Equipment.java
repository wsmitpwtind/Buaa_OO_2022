import java.math.BigInteger;

public class Equipment implements ValueBody
{
    private int id;
    private String name;
    private BigInteger price = new BigInteger("0");

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

    public double useHealth() {
        return 0;
    }

    public double useExp(double advExp) {
        return advExp;
    }

    public double useMoney() {
        return 0;
    }

    public void usePrint(String advName, double advExp)
    {

    }

    public void used(){

    }

    @Override
    public void use(Adventurer user) {

    }
}
