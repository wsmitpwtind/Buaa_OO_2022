import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term {
    private List<Factor> factors;

    private String oriString;

    private List<String> splitStrings = new ArrayList<>();

    public BigInteger[] getAnswer() {
        return answer;
    }

    public void setAnswer(BigInteger[] answer) {
        this.answer = answer;
    }

    private BigInteger[] answer = new BigInteger[9];

    public void prints() {
        for (String item : this.splitStrings) {
            System.out.println(item);
        }
    }

    public Term(String oriString) {
        this.factors = new ArrayList<>();
        this.oriString = oriString.trim();
        this.splitStrings = new ArrayList<>();
        generateFactors();
        generateAnswer();
        /*
        System.out.println("term = " + this.oriString);
        System.out.println("answer = ");
        for (int i = 0; i <= 8; i++) {
            System.out.print( answer[i] + " ");
        }
        System.out.println();

         */

        //System.out.println("new term = "+oriString + " answer = "+this.answer[0]+this.answer[1]);
    }

    public void setFactors(List<Factor> factors) {
        this.factors = factors;
    }

    public List<Factor> getFactors() {
        return factors;
    }

    private static final String DEVICE = "(?<!\\*)\\*(?!(([^\\(]*\\))|(\\*)))";

    private static final Pattern DEVICE_PATTERN = Pattern.compile(DEVICE);

    public void generateFactors() {
        Matcher matcherDate = DEVICE_PATTERN.matcher(this.oriString);
        int end = 0;
        while (matcherDate.find()) {
            int start = matcherDate.start();
            splitStrings.add(this.oriString.substring(end, start).trim());
            factors.add(new Factor(this.oriString.substring(end, start).trim()));
            end = start + 1;
        }
        splitStrings.add(this.oriString.substring(end).trim());
        factors.add(new Factor(this.oriString.substring(end).trim()));
    }

    public void generateAnswer() {
        BigInteger[] tempAnswer = this.factors.get(0).getAnswer();
        BigInteger[] tempAnswerPlus = new BigInteger[9];
        for (int i = 1; i < this.factors.size(); i++) {
            for (int j = 0; j <= 8; j++) {
                BigInteger temp = new BigInteger(String.valueOf(0));
                for (int k = 0; k <= j; k++) {
                    temp = temp.add(this.factors.get(i).getAnswer()[k].multiply(tempAnswer[j - k]));
                }
                tempAnswerPlus[j] = temp;
            }
            for (int j = 0; j <= 8; j++) {
                tempAnswer[j] = tempAnswerPlus[j];
            }
        }
        this.answer = tempAnswer;
    }

    private BigInteger[] multi(BigInteger[][] answerSet) {
        BigInteger[] tempAnswer = answerSet[0];
        for (int i = 1; i < answerSet.length; i++) {
            for (int j = 8; j >= 0; j--) {
                for (int k = 0; k <= j; k++) {
                    tempAnswer[j] = tempAnswer[j].add(answerSet[i][k].multiply(tempAnswer[j - k]));
                }
            }
        }
        return tempAnswer;
    }
}
