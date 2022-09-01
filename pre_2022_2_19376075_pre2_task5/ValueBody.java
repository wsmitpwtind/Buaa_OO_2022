import java.math.BigInteger;

public interface ValueBody {
    void use(Adventurer user);

    int getId();

    BigInteger getPrice();
}
