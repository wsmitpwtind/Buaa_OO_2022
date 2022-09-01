import java.math.BigInteger;
import java.util.HashMap;

public class Factor implements Polynomial {
    private boolean isParentheses;
    private boolean isNegative;
    private boolean isSin;
    private boolean isCos;
    private String triAngel;
    private UserDefineFunction udf;

    private BigInteger index;
    private BigInteger coefficient;
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
                sinList = new SinList(new String[]{triAngel});
                CoefficientList coefficientList = new CoefficientList(
                        new ValueList[]{numberList, sinList, null});
                ans.put(coefficientList, coefficient);
            } else if (!isSin) {
                numberList = new NumberList(index);
                cosList = new CosList(new String[]{triAngel});
                CoefficientList coefficientList = new CoefficientList(
                        new ValueList[]{numberList, null, cosList});
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
            this.triAngel = "";
            this.coefficient = BigInteger.valueOf(1);
            this.index = BigInteger.valueOf(1);
        } else {
            this.isParentheses = false;
            this.parentheses = null;
            this.isSin = false;
            this.isCos = false;
            this.triAngel = "";
            this.coefficient = new BigInteger(inputStr);
            this.index = BigInteger.valueOf(0);
        }
    }

    public Boolean udFunction(String inputStr, UserDefineFunction udf) {
        if (inputStr.charAt(0) == 's' && inputStr.charAt(1) == 'i') {
            this.isParentheses = false;
            this.parentheses = null;
            this.isSin = true;
            this.isCos = false;
            String tri = deleteUseLess(inputStr.substring(4, inputStr.length() - 1));
            if (tri.charAt(0) == '-') {
                this.triAngel = tri.substring(1);
                this.coefficient = BigInteger.valueOf(-1);
            } else if (tri.equals("0")) {
                this.triAngel = "";
                this.isSin = false;
                this.coefficient = BigInteger.valueOf(0);
            } else {
                this.triAngel = tri;
                this.coefficient = BigInteger.valueOf(1);
            }
            this.index = BigInteger.valueOf(0);
            return true;
        } else if (inputStr.charAt(0) == 'c') {
            this.isParentheses = false;
            this.parentheses = null;
            this.isSin = false;
            this.isCos = true;
            String tri = deleteUseLess(inputStr.substring(4, inputStr.length() - 1));
            if (tri.charAt(0) == '-') {
                this.triAngel = tri.substring(1);
            } else if (tri.equals("0")) {
                this.triAngel = "";
                this.isCos = false;
            } else {
                this.triAngel = tri;
            }
            this.coefficient = BigInteger.valueOf(1);
            this.index = BigInteger.valueOf(0);
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
        this.triAngel = "";
        this.coefficient = BigInteger.valueOf(0);
        this.index = BigInteger.valueOf(0);
    }

    private String deleteUseLess(String string) {
        String answer = string;
        answer = answer.replaceAll("\\(", "");
        answer = answer.replaceAll("\\)", "");
        Boolean var = false;
        Boolean exp = false;
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == 'x') {
                var = true;
            }
            if (answer.charAt(i) == '^') {
                exp = true;
            }
        }
        if (!var && !exp) {
            return new BigInteger(answer).toString();
        } else if (var && !exp) {
            return answer;
        } else if (!var && exp) {
            return answer;
        } else {
            String[] split = answer.split("\\^");
            if (new BigInteger(split[1]).equals(BigInteger.ZERO))
            {
                return "1";
            }
            else
            {
                return answer;
            }
        }
    }

}
