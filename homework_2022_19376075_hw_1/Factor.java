import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Factor {
    private BigInteger ratio;

    private int exponent;

    private Expr expr;

    private BigInteger[] answer;

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }


    private static final String EXPR_EXPX = "(?!\\)[\\t ]*\\*\\*[\\t ]*)[-\\+]?[0-9]+$";
    private static final Pattern EXPR_EXPX_PATTERN = Pattern.compile(EXPR_EXPX);

    private static final String EXPR_EXP1 = "\\)[\\t ]*$";
    private static final Pattern EXPR_EXP1_PATTERN = Pattern.compile(EXPR_EXP1);

    private static final String EXPR_EXP = "(?!^\\().+(?=\\)[^\\(]*$)";
    private static final Pattern EXPR_EXP_PATTERN = Pattern.compile(EXPR_EXP);

    private static final String VARIATE = "x";
    private static final Pattern VARIATE_PATTERN = Pattern.compile(VARIATE);

    private static final String CONSTANT = "(?<=\\+?)[-\\+]?[0-9]+";
    private static final Pattern CONSTANT_PATTERN = Pattern.compile(CONSTANT);

    private static final String EXP = "(?<=\\*\\*[\\t ]{0,200})[-\\+]?[0-9]+$";
    private static final Pattern EXP_PATTERN = Pattern.compile(EXP);

    public String getOriString() {
        return oriString;
    }

    private final String oriString;

    public Factor(String oriString) {
        this.exponent = 1;
        this.answer = new BigInteger[9];
        for (int i = 0; i <= 8; i++) {
            this.answer[i] = BigInteger.valueOf(0);
        }
        if (oriString.length() >= 1) {
            if (oriString.charAt(0) == '-') {
                this.oriString = oriString.substring(1);
                this.ratio = BigInteger.valueOf(-1);
            } else {
                this.oriString = oriString;
                this.ratio = BigInteger.valueOf(1);
            }
        } else {
            this.oriString = oriString;
            this.ratio = BigInteger.valueOf(0);
        }
        generateExpr();
        generateAnswer();

        /*
        System.out.println("factor = " + this.oriString);
        System.out.println("ratio = " + this.ratio);
        System.out.println("answer = ");
        for (int i = 0; i <= 8; i++) {
            System.out.print( answer[i] + " ");
        }
        System.out.println();


         */



    }

    public void generateExpr() {
        Matcher matcherExprExpx = EXPR_EXPX_PATTERN.matcher(this.oriString);
        Matcher matcherExprExp1 = EXPR_EXP1_PATTERN.matcher(this.oriString);
        Matcher matcherExprExp = EXPR_EXP_PATTERN.matcher(this.oriString);
        Matcher matcherVariate = VARIATE_PATTERN.matcher(this.oriString);
        Matcher matcherConstant = CONSTANT_PATTERN.matcher(this.oriString);
        Matcher matcherExp = EXP_PATTERN.matcher(this.oriString);
        if (matcherExprExp.find()) {
            if (matcherExprExpx.find()) {
                ratio = ratio.multiply(BigInteger.valueOf(1));
                exponent = Integer.parseInt(matcherExprExpx.group(0));
                expr = new Expr(matcherExprExp.group(0));
            } else if (matcherExprExp1.find()) {
                ratio = ratio.multiply(BigInteger.valueOf(1));
                exponent = 1;
                expr = new Expr(matcherExprExp.group(0));
            }
        } else if (matcherExp.find()) {
            if (matcherVariate.find()) {
                ratio = ratio.multiply(BigInteger.valueOf(1));
                exponent = Integer.parseInt(matcherExp.group(0));
                expr = null;
            } else if (matcherConstant.find()) {
                ratio = ratio.multiply(new BigInteger(matcherConstant.group(0)));
                exponent = Integer.parseInt(matcherExp.group(0));
                expr = null;
            }
        } else {
            if (matcherVariate.find()) {
                if (this.oriString.charAt(0) == '-') {
                    ratio = ratio.multiply(BigInteger.valueOf(-1));
                } else {
                    ratio = ratio.multiply(BigInteger.valueOf(1));
                }
                exponent = 1;
                expr = null;
            } else if (matcherConstant.find()) {
                ratio = ratio.multiply(new BigInteger(matcherConstant.group(0)));
                exponent = 1;
                expr = null;
            }
        }
    }

    public void generateAnswer() {
        if (this.expr != null) {
            if (this.exponent == 0) {
                this.answer[0] = this.ratio;
                for (int i = 1; i <= 8; i++) {
                    this.answer[i] = BigInteger.valueOf(0);
                }
            } else {
                BigInteger[] tempAnswer = this.expr.getAnswer().clone();
                BigInteger[] tempAnswerPlus = new BigInteger[9];
                for (int i = 1; i < this.exponent; i++) {
                    for (int j = 8; j >= 0; j--) {
                        tempAnswerPlus[j] = new BigInteger(String.valueOf(0));
                    }
                    for (int j = 8; j >= 0; j--) {
                        for (int k = 0; k <= j; k++) {
                            if (tempAnswerPlus[j] == null) {
                                tempAnswerPlus[j] = new BigInteger(String.valueOf(0));
                            }
                            tempAnswerPlus[j] = tempAnswerPlus[j].add(
                                    this.expr.getAnswer()[k].multiply(tempAnswer[j - k]));
                        }
                    }
                    for (int j = 8; j >= 0; j--) {
                        tempAnswer[j] = tempAnswerPlus[j];
                    }
                }
                this.answer = tempAnswer.clone();
                for (int j = 8; j >= 0; j--) {
                    this.answer[j] = this.answer[j].multiply(this.ratio);
                }

            }
        } else {
            Matcher matcherVariate = VARIATE_PATTERN.matcher(this.oriString);
            Matcher matcherConstant = CONSTANT_PATTERN.matcher(this.oriString);
            if (matcherVariate.find()) {
                for (int i = 0; i <= 8; i++) {
                    this.answer[i] = BigInteger.valueOf(0);
                }
                this.answer[this.exponent] = this.ratio;
            } else if (matcherConstant.find()) {
                for (int i = 0; i <= 8; i++) {
                    this.answer[i] = BigInteger.valueOf(0);
                }
                this.answer[0] = this.ratio.pow(this.exponent);
                if (this.exponent == 0)
                {
                    if (this.ratio.max(BigInteger.valueOf(0)).equals(BigInteger.valueOf(0)))
                    {
                        this.answer[0] = BigInteger.valueOf(-1);
                    }
                }
            }
        }
    }

    public void setRatio(BigInteger ratio) {
        this.ratio = ratio;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    public void setVarIndex(Expr expr) {
        this.expr = expr;
    }

    public void setAnswer(BigInteger[] answer) {
        this.answer = answer;
    }

    public BigInteger getRatio() {
        return ratio;
    }

    public int getExponent() {
        return exponent;
    }

    public Expr getVarIndex() {
        return expr;
    }

    public BigInteger[] getAnswer() {
        return answer;
    }

}
