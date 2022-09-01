import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.Collections;

public class Expression implements Polynomial {
    public ArrayList<Polynomial> getItems() {
        return items;
    }

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
        ans = deleteZero(ans);
        ans = generateSame(ans);
        ans = sort(ans);
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
        ans = generateSame(ans);
        return ans;
    }

    public String toString()
    {
        HashMap<CoefficientList, BigInteger> ans = calculate();
        ans = deleteZero(ans);
        ans = generateSame(ans);
        String str = print(ans);
        return str;
    }

    private HashMap<CoefficientList, BigInteger> deleteZero(
            HashMap<CoefficientList, BigInteger> answer) {
        HashMap<CoefficientList, BigInteger> finalAnswer = new HashMap<>(answer);
        Map<CoefficientList, BigInteger> mapAnswer = answer;
        for (Map.Entry<CoefficientList, BigInteger> entry : mapAnswer.entrySet()) {
            if (Objects.equals(entry.getValue(), BigInteger.ZERO)) {
                finalAnswer.remove(entry.getKey(), entry.getValue());
            }
        }
        return finalAnswer;
    }

    private HashMap<CoefficientList, BigInteger> generateSame(
            HashMap<CoefficientList, BigInteger> answer) {
        HashMap<CoefficientList, BigInteger> finalAnswer = new HashMap<>();
        Map<CoefficientList, BigInteger> mapAnswer = answer;
        Map<CoefficientList, BigInteger> mapFinalAnswer = finalAnswer;
        for (Map.Entry<CoefficientList, BigInteger> entry : mapAnswer.entrySet()) {
            boolean find = false;
            //System.out.println(entry.getValue());
            for (Map.Entry<CoefficientList, BigInteger> entryFinal : mapFinalAnswer.entrySet()) {
                if (entryFinal.getKey().equals(entry.getKey())) {
                    find = true;
                    mapFinalAnswer.put(
                            entryFinal.getKey(), entry.getValue().add(entryFinal.getValue()));
                    break;
                }
            }
            if (!find) {
                mapFinalAnswer.put(entry.getKey(), entry.getValue());
            }
        }
        return finalAnswer;
    }

    private HashMap<CoefficientList, BigInteger> sort(HashMap<CoefficientList, BigInteger> answer)
    {
        List<Map.Entry<CoefficientList, BigInteger>> infoIds = new ArrayList<>(answer.entrySet());
        Collections.sort(infoIds, (o1, o2) ->
                (o1.getValue().max(o2.getValue()).equals(o1.getValue()) ? 1 : 0));
        HashMap<CoefficientList, BigInteger> finalAnswer = new HashMap<>();
        infoIds.forEach(listMap -> {
            finalAnswer.put(listMap.getKey(), listMap.getValue());
        });
        return finalAnswer;
    }

    private String print(HashMap<CoefficientList, BigInteger> answer) {
        StringBuilder string = new StringBuilder();
        Map<CoefficientList, BigInteger> map = answer;
        List<Map.Entry<CoefficientList, BigInteger>> infoIds = new ArrayList<>(map.entrySet());
        for (Map.Entry<CoefficientList, BigInteger> infoId : infoIds) {
            boolean notZero = true;
            if (infoId.getValue().equals(new BigInteger("1"))) {
                string.append("+");
            } else if (infoId.getValue().equals(new BigInteger("-1"))) {
                string.append("-");
            } else if (!infoId.getValue().equals(new BigInteger("0"))) {
                if (infoId.getValue().max(new BigInteger("0")).equals(new BigInteger("0"))) {
                    string.append(infoId.getValue());
                } else {
                    string.append("+").append(infoId.getValue().toString());
                }
            } else {
                notZero = false;
            }
            if (notZero) {
                plus(string,infoId);
            }
        }
        return finalNormalize(String.valueOf(string));
    }

    private StringBuilder plus(
            StringBuilder string, Map.Entry<CoefficientList, BigInteger> infoId)
    {
        if (!Objects.equals(infoId.getKey().getNumber().getNumber(), new BigInteger("0"))) {
            if (string.charAt(string.length() - 1) != '+' &&
                    string.charAt(string.length() - 1) != '-') {
                string.append("*");
            }
            if (Objects.equals(infoId.getKey().getNumber().getNumber(),
                    new BigInteger(String.valueOf(1)))) {
                string.append("x");
            } else {
                string.append("x^").append(infoId.getKey().
                        getNumber().getNumber().toString());
            }
        }
        plusSin(string, infoId);
        plusCos(string, infoId);
        if (string.charAt(string.length() - 1) == '+' ||
                string.charAt(string.length() - 1) == '-') {
            string.append("1");
        }
        return string;
    }

    private void plusSin(
            StringBuilder string, Map.Entry<CoefficientList, BigInteger> infoId)
    {
        if (infoId.getKey().getSin() != null) {
            String preItem = "";
            BigInteger count = new BigInteger("1");
            for (String item : infoId.getKey().getSin().getValues()) {
                if (preItem.equals(item))
                {
                    count = count.add(new BigInteger("1"));
                    //System.out.println(count);
                } else {
                    if (preItem.equals(""))
                    {
                        if (!(string.charAt(string.length() - 1) == '+' ||
                                string.charAt(string.length() - 1) == '-')) {
                            string.append("*");
                        }
                        string.append("sin(").append(item).append(")");
                        preItem = item;
                    }
                    else
                    {
                        if (!count.equals(new BigInteger("1"))) {
                            string.append("^").append(count.toString());
                        }
                        if (!(string.charAt(string.length() - 1) == '+' ||
                                string.charAt(string.length() - 1) == '-')) {
                            string.append("*");
                        }
                        string.append("sin(").append(item).append(")");
                        preItem = item;
                        count = BigInteger.valueOf(1);
                        //System.out.println(preItem);
                    }
                }
            }
            if (count.max(new BigInteger("2")).equals(count))
            {
                string.append("^").append(count.toString());
            }
        }
    }

    private void plusCos(
            StringBuilder string, Map.Entry<CoefficientList, BigInteger> infoId)
    {
        if (infoId.getKey().getCos() != null) {
            String preItem = "";
            BigInteger count = new BigInteger("1");
            for (String item : infoId.getKey().getCos().getValues()) {
                if (preItem.equals(item))
                {
                    count = count.add(new BigInteger("1"));
                    //System.out.println(count);
                } else {
                    if (preItem.equals(""))
                    {
                        if (!(string.charAt(string.length() - 1) == '+' ||
                                string.charAt(string.length() - 1) == '-')) {
                            string.append("*");
                        }
                        string.append("cos(").append(item).append(")");
                        preItem = item;
                    }
                    else
                    {
                        if (!count.equals(new BigInteger("1"))) {
                            string.append("^").append(count.toString());
                        }
                        if (!(string.charAt(string.length() - 1) == '+' ||
                                string.charAt(string.length() - 1) == '-')) {
                            string.append("*");
                        }
                        string.append("cos(").append(item).append(")");
                        preItem = item;
                        count = BigInteger.valueOf(1);
                        //System.out.println(preItem);
                    }
                }
            }
            if (count.max(new BigInteger("2")).equals(count))
            {
                string.append("^").append(count.toString());
            }
        }
    }

    private String finalNormalize(String string)
    {
        if (string.equals("")) {
            return "0";
        } else {
            //System.out.println(string);
            return  string.replaceAll("\\^\\+", "^")
                    .replaceAll("^\\+", "");
        }
    }
}
