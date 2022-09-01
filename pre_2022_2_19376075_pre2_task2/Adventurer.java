import java.util.ArrayList;
import java.math.BigInteger;

public class Adventurer {
    private int id;
    private String name;
    private ArrayList<Bottle> bottles;

    public void setAdventurer(int id, String name) {
        this.id = id;
        this.name = name;
        bottles = new ArrayList<>();
    }

    public void createBottle(int botId, String botName, long botPrice, double botCapacity) {
        Bottle bottle = new Bottle();
        bottle.setId(botId);
        bottle.setName(botName);
        bottle.setPrice(botPrice);
        bottle.setCapacity(botCapacity);
        bottle.setFilled(true);
        bottles.add(bottle);
    }

    public void deleteBottle(int botId) {
        for (Bottle item : bottles) {
            if (item.getId() == botId) {
                bottles.remove(item);
                break;
            }
        }
    }

    public BigInteger sumBottlePrice() {
        BigInteger sumPrice = new BigInteger("0");
        for (Bottle item : bottles) {
            sumPrice = sumPrice.add(BigInteger.valueOf(item.getPrice()));
        }
        return sumPrice;
    }

    public long maxBottlePrice() {
        long maxPrice = 0x8000000000000000L;
        for (Bottle item : bottles) {
            if (item.getPrice() > maxPrice) {
                maxPrice = item.getPrice();
            }
        }
        return maxPrice;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
