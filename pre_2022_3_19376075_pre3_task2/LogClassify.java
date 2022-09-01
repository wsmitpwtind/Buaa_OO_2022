import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogClassify {
    private final String priLetter = "^\\d{1,4}/\\d{1,2}/\\d{1,2}-" +
            "[A-Za-z0-9]+@[A-Za-z0-9]+\\s:\"[^@]*\"";
    private final String pubLetter = "^\\d{1,4}/\\d{1,2}/\\d{1,2}-[A-Za-z0-9]+:\"[^@]*\"";
    private final String pubToPriLetter = "^\\d{1,4}/\\d{1,2}/\\d{1,2}-[A-Za-z0-9]+" +
            ":\"[^@]*@[A-Za-z0-9]+\\s[^@]*\"";

    private final Pattern priPattern = Pattern.compile(priLetter);
    private final Pattern pubPattern = Pattern.compile(pubLetter);
    private final Pattern pubToPriPattern = Pattern.compile(pubToPriLetter);

    private final String date = "^\\d{1,4}/\\d{1,2}/\\d{1,2}";
    private final Pattern datePattern = Pattern.compile(date);

    private final String send = "-[A-Za-z0-9]+";
    private final Pattern sendPattern = Pattern.compile(send);

    private final String receive = "@[A-Za-z0-9]+\\s";
    private final Pattern receivePattern = Pattern.compile(receive);

    public List<String> letterPattern(String letter) {
        List<String> situation = new ArrayList<>();
        Matcher matcherDate = datePattern.matcher(letter);
        situation.add(matcherDate.group());
        Matcher matcherSend = sendPattern.matcher(letter);
        situation.add(matcherSend.group().substring(1));
        Matcher matcherReceive = receivePattern.matcher(letter);
        situation.add(matcherReceive.group().substring(1, matcherReceive.group().length() - 1));

        for (String item : situation) {
            System.out.println(item);
        }
        return situation;
    }
}
