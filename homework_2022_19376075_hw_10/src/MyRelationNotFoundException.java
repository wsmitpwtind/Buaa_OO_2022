import com.oocourse.spec2.exceptions.RelationNotFoundException;

import java.math.BigInteger;
import java.util.HashMap;

public class MyRelationNotFoundException extends RelationNotFoundException {
    private static HashMap<Integer, BigInteger> errorCount = new HashMap<>();
    private static BigInteger sum = BigInteger.valueOf(0);
    private static String message;

    public MyRelationNotFoundException(int id1, int id2) {
        int temp1 = Math.min(id1, id2);
        sum = sum.add(BigInteger.ONE);
        errorCount.compute(temp1, (k, v) -> {
            if (v == null) {
                return BigInteger.ONE;
            }
            return v.add(BigInteger.ONE);
        });

        int temp2 = Math.max(id1, id2);
        errorCount.compute(temp2, (k, v) -> {
            if (v == null) {
                return BigInteger.ONE;
            }
            return v.add(BigInteger.ONE);
        });
        message = "rnf-" + sum + ", " + temp1 + "-" + errorCount
                .get(temp1) + ", " + temp2 + "-" + errorCount.get(temp2);
    }

    @Override
    public void print() {
        System.out.println(message);
    }
}