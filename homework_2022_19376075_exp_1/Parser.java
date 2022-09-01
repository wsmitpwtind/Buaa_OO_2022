import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final Pattern numberPattern = Pattern.compile("0|([1-9][0-9]*)|[a-zA-Z]+");
    private final HashMap<String, Integer> parameters;
    
    public Parser(HashMap<String, Integer> parameters) {
        this.parameters = parameters;
    }
    
    public Operator parse(String expression) {
        int position = findAddOrSub(expression);
        if (position != -1) {
            if (expression.charAt(position) == '+') {
                return new Add(parse(expression.substring(0, position)),
                        parse(expression.substring(position + 1)));
            } else {
                return new Sub(parse(expression.substring(0, position)),
                        parse(expression.substring(position + 1)));
            }
        } else {
            position = findMul(expression);
            if (position != -1) {
                return new Mul(parse(expression.substring(0, position))
                        , parse(expression.substring(position + 1)));
            } else {
                if (!expression.equals("")) {
                    Matcher matcher = numberPattern.matcher(expression);
                    if (matcher.find()) {
                        String termStr = matcher.group(0);
                        if (parameters.get(termStr) == null)
                        {
                            return new Num(Integer.parseInt(termStr));
                        }
                        else
                        {
                            return new Num(parameters.get(termStr));
                        }
                    }
                    else
                    {
                        return new Num(0);
                    }

                } else {
                    return new Num(0);
                }
            }
        }
    }

    private int findAddOrSub(String expression) {
        int position = -1;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+' || expression.charAt(i) == '-') {
                position = i;
            }
        }
        return position;
    }

    private int findMul(String expression) {
        int position = -1;

        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '*') {
                position = i;
            }
        }
        return position;
    }
}
