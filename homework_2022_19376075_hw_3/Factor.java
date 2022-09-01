import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Factor implements Polynomial {
    public boolean isParentheses() {
        return isParentheses;
    }

    private boolean isParentheses;
    private boolean isNegative;
    private boolean isSin;
    private boolean isCos;
    private UserDefineFunction udf;

    private BigInteger index;
    private BigInteger coefficient;
    private Polynomial triAngel;
    private Polynomial parentheses;

    @Override
    public HashMap<CoefficientList, BigInteger> calculate() {
        HashMap<CoefficientList, BigInteger> ans = new HashMap<>();
        NumberList numberList;
        SinList sinList;
        CosList cosList;
        if (!isParentheses) {
            if (!isSin && !isCos) {
                numberList = new NumberList(index);
                CoefficientList coefficientList = new CoefficientList(
                        new ValueList[]{numberList, null, null});
                ans.put(coefficientList, coefficient);
            } else if (isSin && !isCos) {
                numberList = new NumberList(index);
                ArrayList<String> stringList = fuck(index, triAngel);
                sinList = new SinList(stringList);
                CoefficientList coefficientList;
                if (stringList.size() == 1 && stringList.get(0) == "0") {
                    coefficientList = new CoefficientList(
                            new ValueList[]{numberList, null, null});
                    ans.put(coefficientList, BigInteger.ZERO);
                } else if (stringList.size() == 1 && stringList.get(0).charAt(0) == '-') {
                    stringList.add(stringList.get(0).substring(1,stringList.get(0).length()));
                    stringList.remove(0);
                    sinList = new SinList(stringList);
                    coefficientList = new CoefficientList(
                            new ValueList[]{numberList, sinList, null});
                    ans.put(coefficientList, coefficient.multiply(BigInteger.valueOf(-1)));
                } else {
                    coefficientList = new CoefficientList(
                            new ValueList[]{numberList, sinList, null});
                    ans.put(coefficientList, coefficient);
                }
            } else if (!isSin) {
                numberList = new NumberList(index);
                ArrayList<String> stringList = fuck(index, triAngel);
                cosList = new CosList(stringList);
                CoefficientList coefficientList;
                if (stringList.size() == 1 && stringList.get(0) == "0") {
                    coefficientList = new CoefficientList(
                            new ValueList[]{numberList, null, null});
                } else if (stringList.size() == 1 && stringList.get(0).charAt(0) == '-') {
                    stringList.add(stringList.get(0).substring(1,stringList.get(0).length()));
                    stringList.remove(0);
                    cosList = new CosList(stringList);
                    coefficientList = new CoefficientList(
                            new ValueList[]{numberList, null, cosList});
                } else {
                    coefficientList = new CoefficientList(
                            new ValueList[]{numberList, null, cosList});
                }
                ans.put(coefficientList, coefficient);
            }
        } else {
            ans = parentheses.calculate();
        }
        if (this.isNegative) {
            ans.replaceAll((k, v) -> v.multiply(BigInteger.valueOf(-1)));
        }
        return ans;
    }

    private ArrayList<String> fuck(BigInteger index, Polynomial triAngel)
    {
        ArrayList<String> stringList = new ArrayList<>();
        Expression triExp = (Expression) triAngel;
        if (find(triExp)) {
            stringList.add(triAngel.toString());
        }
        else {
            stringList.add('(' + triAngel.toString() + ')');
        }
        return stringList;
    }

    @Override
    public void setPoly(String input, UserDefineFunction udf) {
        this.udf = udf;
        String inputStr = input;
        if (inputStr.charAt(0) == '-') {
            this.isNegative = true;
            inputStr = inputStr.substring(1);
        } else if (inputStr.charAt(0) == '+') {
            this.isNegative = false;
            inputStr = inputStr.substring(1);
        } else {
            this.isNegative = false;
        }

        if (udFunction(inputStr, udf)) {
            this.udf = udf;
        } else if (inputStr.charAt(0) == 's' && inputStr.charAt(1) == 'u') {
            setParentheses();
            parentheses.setPoly(udf.sumGenerate(inputStr.substring(4, inputStr.length() - 1)), udf);
        } else if (inputStr.charAt(0) == '(') {
            setParentheses();
            parentheses.setPoly(inputStr.substring(1, inputStr.length() - 1), udf);
        } else if (inputStr.charAt(0) == 'x') {
            this.isParentheses = false;
            this.parentheses = null;
            this.isSin = false;
            this.isCos = false;
            this.triAngel = null;
            this.coefficient = BigInteger.valueOf(1);
            this.index = BigInteger.valueOf(1);
        } else {
            this.isParentheses = false;
            this.parentheses = null;
            this.isSin = false;
            this.isCos = false;
            this.triAngel = null;
            this.coefficient = new BigInteger(inputStr);
            this.index = BigInteger.valueOf(0);
        }

    }

    public Boolean udFunction(String inputStr, UserDefineFunction udf) {
        if (inputStr.charAt(0) == 's' && inputStr.charAt(1) == 'i') {
            this.isSin = true;
            this.isCos = false;
            setTriAngel();
            triAngel.setPoly(inputStr.substring(4, inputStr.length() - 1), udf);
            return true;
        } else if (inputStr.charAt(0) == 'c') {
            this.isSin = false;
            this.isCos = true;
            setTriAngel();
            triAngel.setPoly(inputStr.substring(4, inputStr.length() - 1), udf);
            return true;
        } else if (inputStr.charAt(0) == 'f') {
            setParentheses();
            parentheses.setPoly(udf.fsGenerate(inputStr.substring(2, inputStr.length() - 1)), udf);
            return true;
        } else if (inputStr.charAt(0) == 'g') {
            setParentheses();
            parentheses.setPoly(udf.gsGenerate(inputStr.substring(2, inputStr.length() - 1)), udf);
            return true;
        } else if (inputStr.charAt(0) == 'h') {
            setParentheses();
            parentheses.setPoly(udf.hsGenerate(inputStr.substring(2, inputStr.length() - 1)), udf);
            return true;
        } else {
            return false;
        }
    }

    private void setParentheses() {
        this.isParentheses = true;
        this.parentheses = new Expression();
        this.isSin = false;
        this.isCos = false;
        this.triAngel = null;
        this.coefficient = BigInteger.valueOf(1);
        this.index = BigInteger.valueOf(0);
    }

    private void setTriAngel() {
        this.isParentheses = false;
        this.parentheses = null;
        this.triAngel = new Expression();
        this.coefficient = BigInteger.valueOf(1);
        this.index = BigInteger.valueOf(0);
    }

    private boolean find(Expression expression) {
        boolean finder = true;
        Expression findExpression = expression;
        HashMap<CoefficientList,BigInteger> calculate = findExpression.calculate();
        if (calculate.size() > 1)
        {
            return false;
        }
        else if (calculate.size() == 1)
        {
            int type = 0;
            BigInteger bigInteger = null;
            Set<Map.Entry<CoefficientList, BigInteger>> entrySet = calculate.entrySet();
            for (Map.Entry<CoefficientList, BigInteger> node : entrySet)
            {
                type = node.getKey().isNotExpression();
                bigInteger = node.getValue();
            }
            if (type == 1)
            {
                return true;
            } else if (type == 2 && bigInteger.equals(BigInteger.ONE)) {
                return true;
            }
            else {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

}
