import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    private static final List<Object> LOG = new ArrayList<>();

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        String inString;
        List<String> situation;
        inString = scanner.nextLine();
        while (!inString.equals("END_OF_MESSAGE")) {
            inString = inString.trim();
            String[] records = inString.split(";");
            for (String item : records) {
                item = item.trim() + ";";
                situation = letterPattern(item);
                situation.add(item);
                LOG.add(situation);
            }
            inString = scanner.nextLine();
        }
        String questionType;
        String questionData;
        while (scanner.hasNext()) {
            inString = scanner.nextLine();
            inString = inString.trim();
            String[] records = inString.split("\\s+");
            questionType = records[0];
            questionData = records[1];
            switch (questionType) {
                case "qdate":
                    for (Object item : LOG) {
                        if (Objects.equals(((List<?>) item).get(0), questionData)) {
                            System.out.println(((List<?>) item).get(3));
                        }
                    }
                    break;
                case "qsend":
                    for (Object item : LOG) {
                        if (Objects.equals(((List<?>) item).get(1), questionData)) {
                            System.out.println(((List<?>) item).get(3));
                        }
                    }
                    break;
                case "qrecv":
                    for (Object item : LOG) {
                        if (Objects.equals(((List<?>) item).get(2), questionData)) {
                            System.out.println(((List<?>) item).get(3));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private static final String DATE = "^\\d{1,4}/\\d{1,2}/\\d{1,2}";
    private static final Pattern DATE_PATTERN = Pattern.compile(DATE);

    private static final String SEND = "-[A-Za-z0-9]+";
    private static final Pattern SEND_PATTERN = Pattern.compile(SEND);

    private static final String RECEIVE = "@[A-Za-z0-9]+\\s";
    private static final Pattern RECEIVE_PATTERN = Pattern.compile(RECEIVE);

    public static List<String> letterPattern(String letter) {
        List<String> situation = new ArrayList<>();
        Matcher matcherDate = DATE_PATTERN.matcher(letter);
        if (matcherDate.find()) {
            situation.add(matcherDate.group());
        } else {
            situation.add("");
        }
        Matcher matcherSend = SEND_PATTERN.matcher(letter);
        if (matcherSend.find()) {
            situation.add(matcherSend.group().substring(1));
        } else {
            situation.add("");
        }
        Matcher matcherReceive = RECEIVE_PATTERN.matcher(letter);
        if (matcherReceive.find()) {
            situation.add(matcherReceive.group().substring(1, matcherReceive.group().length() - 1));
        } else {
            situation.add("");
        }
        return situation;
    }

}