import com.oocourse.spec1.ExprInput;
import com.oocourse.spec1.ExprInputMode;

import java.math.BigInteger;

public class MainClass {
    public static void main(String[] args) {
        //目前bug 指数只能是int，不能Biginteger
        ExprInput scanner = new ExprInput(ExprInputMode.NormalMode);


        String exprString = scanner.readLine();


        exprString = exprString.replaceAll("\\t+", "");
        exprString = exprString.replaceAll(" ", "");
        exprString = exprString.replaceAll("\\++", "+");
        exprString = exprString.replaceAll("-\\+", "-");
        exprString = exprString.replaceAll("\\++", "+");
        exprString = exprString.replaceAll("-{2}+", "+");
        exprString = exprString.replaceAll("-", "+-");
        exprString = exprString.replaceAll("\\++", "+");
        //System.out.println("exprString = " + exprString);
        Expr expr = new Expr(exprString);
        //System.out.println("terms = ");

        BigInteger[] answer = expr.getAnswer();

        /*
        for (int i = 0; i <= 8; i++) {
            System.out.print(answer[i] + " ");
        }
        System.out.println();



         */


        printAnswer(answer);
    }

    private static void printAnswer(BigInteger[] answer) {
        StringBuilder stringBuilder = new StringBuilder();
        Boolean allNegative = true;
        for (int i = 8; i >= 0; i--) {
            if ((answer[i].max(BigInteger.valueOf(0)).equals(answer[i])) &
                    !answer[i].equals(BigInteger.valueOf(0)))
            {
                allNegative = false;
                int primFlag = i;
                printI(primFlag, answer, stringBuilder);
                for (int j = 8; j >= 0; j--) {
                    if (j != primFlag)
                    {
                        printI(j, answer, stringBuilder);
                    }
                }
                break;
            }
        }
        if (allNegative) {
            for (int i = 8; i >= 0; i--) {
                printI(i, answer, stringBuilder);
            }
        }
        String answerString = stringBuilder.toString();

        if (answerString.length() == 0) {
            answerString = "0";
        }
        if (answerString.charAt(0) == '+') {
            answerString = answerString.substring(1);
        }
        System.out.println(answerString);
    }

    private static void printI(int i, BigInteger[] answer, StringBuilder stringBuilder) {
        if (!answer[i].equals(BigInteger.valueOf(0))) {
            if (i > 1) {
                if (answer[i].max(BigInteger.valueOf(0)).equals(answer[i])) {
                    if (!answer[i].equals(BigInteger.valueOf(1))) {
                        stringBuilder.append("+").append(answer[i]).append("*");
                    }
                    else
                    {
                        stringBuilder.append("+");
                    }
                } else {
                    if (answer[i].equals(BigInteger.valueOf(-1))) {
                        stringBuilder.append("-");
                    } else {
                        stringBuilder.append(answer[i]).append("*");
                    }
                }
                if (i == 2) {
                    stringBuilder.append("x*x");
                } else {
                    stringBuilder.append("x**" + i);
                }
            } else if (i == 1) {
                if (answer[i].max(BigInteger.valueOf(0)).equals(answer[i])) {
                    if (!answer[i].equals(BigInteger.valueOf(1))) {
                        stringBuilder.append("+").append(answer[i]).append("*x");
                    }
                    else
                    {
                        stringBuilder.append("+x");
                    }
                } else {
                    if (answer[i].equals(BigInteger.valueOf(-1))) {
                        stringBuilder.append("-x");
                    } else {
                        stringBuilder.append(answer[i]).append("*x");
                    }
                }
                //stringBuilder.append("x");
            } else {
                if (answer[i].max(BigInteger.valueOf(0)).equals(answer[i])) {
                    stringBuilder.append("+").append(answer[i]);
                } else {
                    stringBuilder.append(answer[i]);
                }
            }
        }
    }
}
