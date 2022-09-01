import com.oocourse.spec1.exceptions.EqualRelationException;

import java.math.BigInteger;
import java.util.HashMap;

public class MyEqualRelationException extends EqualRelationException {
    private static HashMap<Integer, BigInteger> errorCount = new HashMap<>();
    private static BigInteger sum = BigInteger.valueOf(0);
    private static String message;

    public MyEqualRelationException(int id1, int id2) {
        int temp1 = Math.min(id1, id2);
        int temp2 = Math.max(id1, id2);
        if (temp1 == temp2) {
            sum = sum.add(BigInteger.ONE);
            errorCount.compute(temp1, (k, v) -> {
                if (v == null) {
                    return BigInteger.ONE;
                }
                return v.add(BigInteger.ONE);
            });
            message = "er-" + sum + ", " + temp1 + "-" + errorCount
                    .get(temp1) + ", " + temp2 + "-" + errorCount.get(temp2);
        } else {
            sum = sum.add(BigInteger.ONE);
            errorCount.compute(temp1, (k, v) -> {
                if (v == null) {
                    return BigInteger.ONE;
                }
                return v.add(BigInteger.ONE);
            });
            errorCount.compute(temp2, (k, v) -> {
                if (v == null) {
                    return BigInteger.ONE;
                }
                return v.add(BigInteger.ONE);
            });
            message = "er-" + sum + ", " + temp1 + "-" + errorCount
                    .get(temp1) + ", " + temp2 + "-" + errorCount.get(temp2);
        }
    }

    @Override
    public void print() {
        System.out.println(message);
    }
}