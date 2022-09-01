import java.math.BigInteger;

public interface ValueBody {
    void setId(int id);

    void setName(String name);

    void setPrice(BigInteger price);

    int getId();

    String getName();

    BigInteger getPrice();

    void use(Adventurer user);
}
