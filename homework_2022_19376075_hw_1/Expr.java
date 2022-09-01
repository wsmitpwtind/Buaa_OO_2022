import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expr {

    private List<Term> terms;

    private String oriString;

    private final List<String> splitStrings;

    private BigInteger[] answer;

    private HashMap<String,BigInteger> answerPlus;

    public void prints() {
        for (String item : this.splitStrings) {
            System.out.println(item);
        }
    }

    private static final String DEVICE = "(?<!\\*[\\t ]{0,200})\\+(?!([^\\(]*\\)))";
    private static final Pattern DEVICE_PATTERN = Pattern.compile(DEVICE);

    public String getOriString() {
        return oriString;
    }

    public void setOriString(String oriString) {
        this.oriString = oriString;
    }


    public Expr(String oriString) {
        this.terms = new ArrayList<>();
        this.oriString = oriString.trim();
        this.splitStrings = new ArrayList<>();
        this.answer = new BigInteger[9];
        for (int i = 0; i <= 8; i++) {
            this.answer[i] = BigInteger.valueOf(0);
        }
        generateTerms();
        generateAnswer();

        /*
        System.out.println("expr = " + this.oriString);
        System.out.println("answer = ");
        for (int i = 0; i <= 8; i++) {
            System.out.print( answer[i] + " ");
        }
        System.out.println();


         */

    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public void generateTerms() {
        Matcher matcherDate = DEVICE_PATTERN.matcher(this.oriString);
        int end = 0;
        while (matcherDate.find()) {
            int start = matcherDate.start();
            splitStrings.add(this.oriString.substring(end, start).trim());
            terms.add(new Term(this.oriString.substring(end, start).trim()));
            end = start + 1;
        }
        splitStrings.add(this.oriString.substring(end).trim());
        terms.add(new Term(this.oriString.substring(end).trim()));
    }

    public void generateAnswer() {
        for (int i = 0; i <= 8; i++) {
            this.answer[i] = BigInteger.valueOf(0);
        }
        for (Term term : this.terms) {
            for (int i = 0; i <= 8; i++) {
                this.answer[i] = this.answer[i].add(term.getAnswer()[i]);
            }
        }
    }

    public BigInteger[] getAnswer() {
        return this.answer;
    }

    public void setAnswer(BigInteger[] answer) {
        this.answer = answer;
    }
}
