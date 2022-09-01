import java.math.BigInteger;
import java.util.HashMap;

public interface Polynomial {

    HashMap<CoefficientList, BigInteger> calculate();

    void setPoly(String input, UserDefineFunction udf);
}
