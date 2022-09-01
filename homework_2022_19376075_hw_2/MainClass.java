import com.oocourse.spec2.ExprInput;
import com.oocourse.spec2.ExprInputMode;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Arrays;
import java.util.ArrayList;

public class MainClass {

    public static void main(String[] args) {
        ExprInput scanner = new ExprInput(ExprInputMode.NormalMode);
        int cnt = scanner.getCount();
        UserDefineFunction udf = new UserDefineFunction();
        for (int i = 0; i < cnt; i++) {
            String func = scanner.readLine();
            func = stringNormalize(func);
            udf.add(func);
        }


        String input = scanner.readLine();

        input = stringNormalize(input);

        Polynomial expression = new Expression();
        //System.out.println(input);
        expression.setPoly(input,udf);

        HashMap<CoefficientList, BigInteger> ans = expression.calculate();

        /*
        Map<CoefficientList, BigInteger> mapAnswer = ans;
        for (Map.Entry<CoefficientList, BigInteger> entry : mapAnswer.entrySet()) {
            System.out.println(entry.getValue());
        }


         */
        ans = deleteZero(ans);
        ans = generateSame(ans);
        print(ans);

    }

    public static HashMap<CoefficientList, BigInteger> deleteZero(
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

    public static HashMap<CoefficientList, BigInteger> generateSame(
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

    public static void print(HashMap<CoefficientList, BigInteger> answer) {
        StringBuilder string = new StringBuilder();
        Map<CoefficientList, BigInteger> map = answer;
        List<Map.Entry<CoefficientList, BigInteger>> infoIds = new ArrayList<>(map.entrySet());
        //infoIds.sort((o1, o2) -> (o2.getValue().equals(o1.getValue()) ? 0 :
        //       o2.getValue().max(o1.getValue()).equals(o2.getValue()) ? 1 : -1));
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
        finalNormalize(String.valueOf(string));
    }

    public static String stringNormalize(String str) {
        return str
                .replaceAll("\\s*", "")
                .replaceAll("\\*\\*", "^")
                .replaceAll("\\^\\+", "^");
    }

    public static StringBuilder plus(
            StringBuilder string, Map.Entry<CoefficientList, BigInteger> infoId)
    {
        if (!Objects.equals(infoId.getKey().getNumber().getNumber(), new BigInteger("0"))) {
            if (string.charAt(string.length() - 1) != '+' &&
                    string.charAt(string.length() - 1) != '-') {
                string.append("*");
            }
            if (Objects.equals(infoId.getKey().getNumber().getNumber(),
                    new BigInteger(String.valueOf(2)))) {
                string.append("x*x");
            } else if (Objects.equals(infoId.getKey().getNumber().getNumber(),
                    new BigInteger(String.valueOf(1)))) {
                string.append("x");
            } else {
                string.append("x**").append(infoId.getKey().
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

    public static void plusSin(
            StringBuilder string, Map.Entry<CoefficientList, BigInteger> infoId)
    {
        if (infoId.getKey().getSin() != null) {
            Arrays.sort(infoId.getKey().getSin().getValues());
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
                            string.append("**").append(count.toString());
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
                string.append("**").append(count.toString());
            }
        }
    }

    public static void plusCos(
            StringBuilder string, Map.Entry<CoefficientList, BigInteger> infoId)
    {
        if (infoId.getKey().getCos() != null) {
            Arrays.sort(infoId.getKey().getCos().getValues());
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
                            string.append("**").append(count.toString());
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
                string.append("**").append(count.toString());
            }
        }
    }

    public static void finalNormalize(String string)
    {
        if (string.equals("")) {
            System.out.println("0");
        } else {
            //System.out.println(string);
            System.out.println(string.replaceAll("\\^\\+", "^")
                    .replaceAll("\\^", "\\*\\*")
                    .replaceAll("^\\+", ""));
        }
    }

}
