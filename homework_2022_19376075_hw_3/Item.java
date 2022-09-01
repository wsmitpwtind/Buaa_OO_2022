import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Item implements Polynomial {
    public ArrayList<Polynomial> getFactors() {
        return factors;
    }

    private final ArrayList<Polynomial> factors;
    private UserDefineFunction udf;
    private boolean isNegative;

    public Item() {
        factors = new ArrayList<>();
    }

    @Override
    public HashMap<CoefficientList, BigInteger> calculate() {
        HashMap<CoefficientList, BigInteger> ans = new HashMap<>();
        CoefficientList coefficientList = new CoefficientList(
                new ValueList[]{new NumberList(BigInteger.valueOf(0)), null, null});
        ans.put(coefficientList, BigInteger.valueOf(1));

        for (Polynomial factor : factors) {
            HashMap<CoefficientList, BigInteger> factorAns = factor.calculate();
            ans = multiply(ans, factorAns);
            //System.out.println(ans.size());
            /*
            Map<CoefficientList, BigInteger> mapAnswer = ans;
            for (Map.Entry<CoefficientList, BigInteger> entry : mapAnswer.entrySet()) {
                System.out.println(entry.getValue());
            }

             */



        }

        HashMap<CoefficientList, BigInteger> negativeOne = new HashMap<>();
        CoefficientList negativeOneCoefficientList = new CoefficientList(
                new ValueList[]{new NumberList(BigInteger.valueOf(0)), null, null});
        negativeOne.put(negativeOneCoefficientList, BigInteger.valueOf(-1));

        if (this.isNegative) {
            ans = multiply(ans, negativeOne);
        }
        ans = deleteZero(ans);
        ans = generateSame(ans);
        return ans;
    }

    public void indexJudge(String factorString, UserDefineFunction udf) {
        boolean findIndex = false;
        for (int j = 0; j < factorString.length(); j++) {
            if (factorString.charAt(j) == '^') {
                if (isFactor(factorString.substring(0, j))) {
                    findIndex = true;
                    int index = Integer.parseInt(factorString.substring(j + 1));
                    if (index == 0) {
                        Polynomial factor = new Factor();
                        if (factorString.charAt(0) == '-') {
                            factor.setPoly("-1", udf);
                        } else {
                            factor.setPoly("1", udf);
                        }
                        factors.add(factor);
                    } else {
                        String power;
                        if (factorString.charAt(0) == '-') {
                            this.isNegative = !this.isNegative;
                            power = factorString.substring(1, j);
                        } else {
                            power = factorString.substring(0, j);
                        }
                        for (int k = 0; k < index; k++) {
                            Polynomial factor = new Factor();
                            factor.setPoly(power, udf);
                            factors.add(factor);
                        }
                    }
                }
            }
        }
        if (!findIndex) {
            Polynomial factor = new Factor();
            factor.setPoly(factorString, udf);
            factors.add(factor);
        }
    }

    @Override
    public void setPoly(String input, UserDefineFunction udf) {
        String inputStr = input;
        this.udf = udf;
        if (inputStr.charAt(0) == '-') {
            this.isNegative = true;
            inputStr = inputStr.substring(1);
        } else if (inputStr.charAt(0) == '+') {
            this.isNegative = false;
            inputStr = inputStr.substring(1);
        } else {
            this.isNegative = false;
        }
        int start = 0;
        for (int i = 0; i < inputStr.length(); i++) {
            if (inputStr.charAt(i) == '*') {
                if (isFactor(inputStr.substring(start, i))) {
                    String factorString = inputStr.charAt(start) == '*' ?
                            inputStr.substring(start + 1, i) :
                            inputStr.substring(start, i);
                    indexJudge(factorString, udf);
                    start = i;
                }
            }
        }

        if (isFactor(inputStr.substring(start))) {
            String factorString = inputStr.charAt(start) == '*' ?
                    inputStr.substring(start + 1) :
                    inputStr.substring(start);
            indexJudge(factorString, udf);
        }
    }

    public boolean isFactor(String input) {
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
                    (judgeChar == 'x' || (judgeChar >= '0' && judgeChar <= '9') ||
                            judgeChar == ')');
        }
        return false;
    }

    public HashMap<CoefficientList, BigInteger> multiply(
            HashMap<CoefficientList, BigInteger> left, HashMap<CoefficientList, BigInteger> right) {
        HashMap<CoefficientList, BigInteger> ans = new HashMap<>();
        NumberList numberList;
        SinList sinList;
        CosList cosList;
        Map<CoefficientList, BigInteger> mapLeft = left;
        Map<CoefficientList, BigInteger> mapRight = right;
        for (Map.Entry<CoefficientList, BigInteger> entryLeft : mapLeft.entrySet()) {
            for (Map.Entry<CoefficientList, BigInteger> entryRight : mapRight.entrySet()) {
                numberList = new NumberList(entryLeft.getKey().getNumber().getNumber().add(
                        entryRight.getKey().getNumber().getNumber()));
                if (entryLeft.getKey().getSin() != null && entryRight.getKey().getSin() != null) {
                    sinList = new SinList(addStringList(entryLeft.getKey().getSin().getValues(),
                            entryRight.getKey().getSin().getValues()));
                } else if (entryLeft.getKey().getSin() == null &&
                        entryRight.getKey().getSin() != null) {
                    sinList = new SinList(entryRight.getKey().getSin().getValues());
                } else if (entryLeft.getKey().getSin() != null &&
                        entryRight.getKey().getSin() == null) {
                    sinList = new SinList(entryLeft.getKey().getSin().getValues());
                } else {
                    sinList = null;
                }
                if (entryLeft.getKey().getCos() != null && entryRight.getKey().getCos() != null) {
                    cosList = new CosList(addStringList(entryLeft.getKey().getCos().getValues(),
                            entryRight.getKey().getCos().getValues()));
                } else if (entryLeft.getKey().getCos() == null &&
                        entryRight.getKey().getCos() != null) {
                    cosList = new CosList(entryRight.getKey().getCos().getValues());
                } else if (entryLeft.getKey().getCos() != null &&
                        entryRight.getKey().getCos() == null) {
                    cosList = new CosList(entryLeft.getKey().getCos().getValues());
                } else {
                    cosList = null;
                }
                CoefficientList coefficientList = new CoefficientList(
                        new ValueList[]{numberList, sinList, cosList});
                if (ans.containsKey(coefficientList)) {
                    ans.put(coefficientList, ans.get(coefficientList).add(
                            entryLeft.getValue().multiply(entryRight.getValue())));
                }
                else {
                    ans.put(coefficientList, entryLeft.getValue().multiply(entryRight.getValue()));
                }
            }
        }
        ans = deleteZero(ans);
        ans = generateSame(ans);
        return ans;
    }

    public ArrayList<String> addStringList(ArrayList<String> a, ArrayList<String> b) {
        ArrayList<String> c = new ArrayList<>(a);
        c.addAll(b);
        return c;
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

}

