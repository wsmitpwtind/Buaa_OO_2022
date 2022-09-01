import java.math.BigInteger;

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
        String[] splitString = string.split(",");
        for (int i = 0; i < splitString.length; i++) {
            char temp = (char) (i + 65);
            answer = answer.replace(String.valueOf(temp), splitString[i]);
        }
        //System.out.println("ans = " + answer);
        return answer;
    }

    public String sumGenerate(String string) {
        StringBuilder answer = new StringBuilder();
        String[] splitString = string.split(",");
        String answerTemp = splitString[3];
        answerTemp = answerTemp.replace("sin","SIN");
        for (BigInteger i = new BigInteger(splitString[1]);
             new BigInteger(splitString[2]).min(i).equals(i); i = i.add(new BigInteger("1"))) {
            answer.append("+").append(answerTemp.replace("i", "(" + i.toString() + ")"));
        }
        //System.out.println(answer);
        if (answer.toString().equals(""))
        {
            return "0";
        }
        else
        {
            return answer.toString().replace("SIN","sin").substring(1,answer.length());
        }
    }
}
