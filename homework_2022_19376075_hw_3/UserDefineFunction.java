import java.math.BigInteger;
import java.util.ArrayList;

public class UserDefineFunction {
    private String fsFunction;
    private String gsFunction;
    private String hsFunction;

    public void add(String string) {
        int flag = 65;
        String answer = string;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == 'x' || string.charAt(i) == 'y' || string.charAt(i) == 'z') {
                answer = answer.replaceAll(String.valueOf(
                        string.charAt(i)), "(" + (char) flag + ")");
                flag++;
            }
        }
        answer = answer.split("=")[1];
        //answer = "(" + answer + ")";
        if (string.charAt(0) == 'f') {
            fsFunction = answer;
        }
        if (string.charAt(0) == 'g') {
            gsFunction = answer;
        }
        if (string.charAt(0) == 'h') {
            hsFunction = answer;
        }
    }

    public String fsGenerate(String string) {
        return getString(string, fsFunction);
    }

    public String gsGenerate(String string) {
        return getString(string, gsFunction);
    }

    public String hsGenerate(String string) {
        return getString(string, hsFunction);
    }

    private String getString(String string, String function) {
        String answer = function;
        ArrayList<String> splitString = new ArrayList<>();
        int count = 0;
        int place = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '(') {
                count++;
            }
            if (string.charAt(i) == ')') {
                count--;
            }
            if ((string.charAt(i) == ',' || i == string.length() - 1) && count == 0) {
                if (i == string.length() - 1) {
                    splitString.add(string.substring(place, i + 1));
                } else {
                    splitString.add(string.substring(place, i));
                }
                place = i + 1;
            }
        }
        for (int i = 0; i < splitString.size(); i++) {
            char temp = (char) (i + 65);
            answer = answer.replace(String.valueOf(temp), splitString.get(i));
        }
        Expression answerExpr = new Expression();
        answerExpr.setPoly(answer,this);
        answer = answerExpr.toString();
        return answer;
    }

    public String sumGenerate(String string) {
        StringBuilder answer = new StringBuilder();
        ArrayList<String> splitString = new ArrayList<>();
        int count = 0;
        int place = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '(') {
                count++;
            }
            if (string.charAt(i) == ')') {
                count--;
            }
            if ((string.charAt(i) == ',' || i == string.length() - 1) && count == 0) {
                if (i == string.length() - 1) {
                    splitString.add(string.substring(place, i + 1));
                } else {
                    splitString.add(string.substring(place, i));
                }
                place = i + 1;
            }
        }
        String answerTemp = splitString.get(3);
        answerTemp = answerTemp.replace("sin", "SIN");
        for (BigInteger i = new BigInteger(splitString.get(1));
             new BigInteger(splitString.get(2)).min(i).equals(i); i = i.add(new BigInteger("1"))) {
            answer.append("+").append(answerTemp.replace("i", "(" + i.toString() + ")"));
        }
        //System.out.println(answer);
        if (answer.toString().equals("")) {
            return "0";
        } else {
            Expression answerExpr = new Expression();
            answerExpr.setPoly(answer.toString().replace(
                    "SIN", "sin").substring(1, answer.length()),this);
            return answerExpr.toString();
        }
    }
}
