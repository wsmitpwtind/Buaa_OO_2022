import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Expression implements Polynomial {
    private final ArrayList<Polynomial> items;
    private UserDefineFunction udf;

    public Expression() {
        items = new ArrayList<>();
    }

    @Override
    public HashMap<CoefficientList, BigInteger> calculate() {
        HashMap<CoefficientList, BigInteger> ans = new HashMap<>();
        CoefficientList coefficientList = new CoefficientList(
                new ValueList[]{new NumberList(BigInteger.valueOf(0)), null, null});
        ans.put(coefficientList, BigInteger.valueOf(0));

        for (Polynomial item : items) {
            HashMap<CoefficientList, BigInteger> itemAns = item.calculate();
            ans = add(ans, itemAns);
            /*
            Map<CoefficientList, BigInteger> mapAnswer = ans;
            for (Map.Entry<CoefficientList, BigInteger> entry : mapAnswer.entrySet()) {
                System.out.println(entry.getValue());
            }

             */
        }
        return ans;
    }

    @Override
    public void setPoly(String input,UserDefineFunction udf) {
        int start = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+' || input.charAt(i) == '-') {
                if (isItem(input.substring(start, i))) {
                    Polynomial item = new Item();
                    //System.out.println(input.substring(start, i));
                    item.setPoly(input.substring(start, i),udf);
                    items.add(item);
                    start = i;
                }
            }
        }

        if (isItem(input.substring(start))) {
            Polynomial item = new Item();
            //System.out.println(input.substring(start));
            item.setPoly(input.substring(start),udf);
            items.add(item);
        }

        this.udf = udf;
    }

    public boolean isItem(String input) {
        int parenthesesCount = 0;
        if (!input.equals("")) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '(') {
                    parenthesesCount++;
                } else if (input.charAt(i) == ')') {
                    parenthesesCount--;
                }
            }
            char judgeChar = input.charAt(input.length() - 1);
            return parenthesesCount == 0 &&
                    (judgeChar == 'x' ||
                            (judgeChar >= '0' && judgeChar <= '9') || judgeChar == ')');
        }
        return false;
    }

    public HashMap<CoefficientList, BigInteger> add(
            HashMap<CoefficientList, BigInteger> left, HashMap<CoefficientList, BigInteger> right) {
        HashMap<CoefficientList, BigInteger> ans = new HashMap<>();
        ans.putAll(left);
        ans.putAll(right);
        ans = deleteZero(ans);
        return ans;
    }

    public static HashMap<CoefficientList, BigInteger> deleteZero(
            HashMap<CoefficientList, BigInteger> answer) {
        HashMap<CoefficientList, BigInteger> finalAnswer = new HashMap<>(answer);
        for (Map.Entry<CoefficientList, BigInteger> entry :
                ((Map<CoefficientList, BigInteger>) answer).entrySet()) {
            if (Objects.equals(entry.getValue(), BigInteger.ZERO)) {
                finalAnswer.remove(entry.getKey(), entry.getValue());
            }
        }
        return finalAnswer;
    }

}
