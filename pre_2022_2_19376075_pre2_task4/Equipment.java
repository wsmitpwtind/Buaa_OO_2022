public class Equipment
{
    private int id;
    private String name;
    private long price;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getPrice() {
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
}
