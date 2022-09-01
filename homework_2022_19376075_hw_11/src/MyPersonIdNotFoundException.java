import com.oocourse.spec3.exceptions.PersonIdNotFoundException;

import java.math.BigInteger;
import java.util.HashMap;

public class MyPersonIdNotFoundException extends PersonIdNotFoundException {
    private static HashMap<Integer, BigInteger> errorCount = new HashMap<>();
    private static BigInteger sum = BigInteger.valueOf(0);
    private static String message;

    public MyPersonIdNotFoundException(int id) {
        sum = sum.add(BigInteger.ONE);
        errorCount.compute(id, (k, v) -> {
            if (v == null) {
                return BigInteger.ONE;
            }
            return v.add(BigInteger.ONE);
        });
        message = "pinf-" + sum + ", " + id + "-" + errorCount.get(id);
    }

    @Override
    public void print() {
        System.out.println(message);
    }
}