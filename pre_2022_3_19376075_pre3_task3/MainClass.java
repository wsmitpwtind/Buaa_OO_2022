import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    private static final List<List<String>> LOG = new ArrayList<>();

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        read(scanner);
        String questionType;
        String questionData;
        String inString;
        while (scanner.hasNext()) {
            inString = scanner.nextLine();
            inString = inString.trim();
            String[] records = inString.split("\\s+");
            questionType = records[0];
            questionData = records[1];
            switch (questionType) {
                case "qdate":
                    questionData = questionData + " ";
                    for (List<String> texts : LOG) {
                        String text = texts.get(6);
                        String[] strArray1 = text.split("-");
                        String[] strArray2 = strArray1[0].split("/");
                        String[] strArray3 = questionData.split("/");
                        int textYear = Integer.parseInt(strArray2[0]);
                        int textMonth = Integer.parseInt(strArray2[1]);
                        int textDay = Integer.parseInt(strArray2[2]);
                        int elementYear = strArray3[0].equals("") ? -1 :
                                Integer.parseInt(strArray3[0]);
                        int elementMonth = strArray3[1].equals("") ? -1 :
                                Integer.parseInt(strArray3[1]);
                        int elementDay = strArray3[2].equals(" ") ? -1 :
                                Integer.parseInt(strArray3[2].trim());
                        if ((textYear == elementYear || elementYear == -1) &&
                                (textMonth == elementMonth || elementMonth == -1) &&
                                (textDay == elementDay || elementDay == -1)) {
                            System.out.println(text);
                        }
                    }
                    break;
                case "qsend":
                    for (Object item : LOG) {
                        if (Objects.equals(((List<?>) item).get(4), questionData)) {
                            System.out.println(((List<?>) item).get(6));
                        }
                    }
                    break;
                case "qrecv":
                    for (Object item : LOG) {
                        if (Objects.equals(((List<?>) item).get(5), questionData)) {
                            System.out.println(((List<?>) item).get(6));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static void read(Scanner scanner)
    {
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
    }

    private static final String DATE = "^\\d{1,4}/\\d{1,2}/\\d{1,2}";
    private static final Pattern DATE_PATTERN = Pattern.compile(DATE);

    private static final String SEND = "-[A-Za-z0-9]+";
    private static final Pattern SEND_PATTERN = Pattern.compile(SEND);

    private static final String RECEIVE = "@[A-Za-z0-9]+\\s";
    private static final Pattern RECEIVE_PATTERN = Pattern.compile(RECEIVE);

    private static final String YEAR = "^\\d{1,4}";
    private static final Pattern YEAR_PATTERN = Pattern.compile(YEAR);

    private static final String MONTH = "^(?<=\\d{1,4})/\\d{1,2}";
    private static final Pattern MONTH_PATTERN = Pattern.compile(MONTH);

    private static final String DAY = "^(?<=\\d{1,4}/\\d{1,2}/)\\){1,2}";
    private static final Pattern DAY_PATTERN = Pattern.compile(DAY);

    public static List<String> letterPattern(String letter) {
        List<String> situation = new ArrayList<>();
        Matcher matcherDate = DATE_PATTERN.matcher(letter);
        if (matcherDate.find()) {
            situation.add(matcherDate.group());
        } else {
            situation.add("");
        }
        String[] type = matcherDate.group().split("/",3);
        situation.add(type[0]);
        situation.add(type[1]);
        situation.add(type[2]);
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