import java.math.BigInteger;

public class NumberList extends ValueList {
    private BigInteger number;

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public NumberList(BigInteger number) {
        this.number = number;
    }

}
