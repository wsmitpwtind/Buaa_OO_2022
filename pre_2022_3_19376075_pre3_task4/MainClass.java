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
                situation = new ArrayList<>();
                item = item.trim() + ";";
                situation.add(letterPattern(item));
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
            for (Object item : LOG) {
                //System.out.println(item);
                String type = inString.substring(6, 7);
                int num = Integer.parseInt(inString.substring(8, 9).trim());
                if (matches(((List<String>) item).get(0), type, num)) {
                    System.out.println(((List<?>) item).get(1));
                }
            }

        }
    }

    private static final String DATE = "\"[A-Za-z0-9@?!,. ]+\"";
    private static final Pattern DATE_PATTERN = Pattern.compile(DATE);

    private static final String A1 = "^a{2,3}?b{2,4}?c{2,4}?";
    private static final Pattern A1_PATTERN = Pattern.compile(A1);
    private static final String A2 = "a{2,3}?b{2,4}?c{2,4}?$";
    private static final Pattern A2_PATTERN = Pattern.compile(A2);
    private static final String A3 = "a{2,3}?b{2,4}?c{2,4}?";
    private static final Pattern A3_PATTERN = Pattern.compile(A3);
    private static final String A4 = "(a+.*?){2,3}?(b+.*?){2,4}?(c+.*?){2,4}?";
    private static final Pattern A4_PATTERN = Pattern.compile(A4);

    private static final String B1 = "^a{2,3}?b{2,1000000}?c{2,4}?";
    private static final Pattern B1_PATTERN = Pattern.compile(B1);
    private static final String B2 = "a{2,3}?b{2,1000000}?c{2,4}?$";
    private static final Pattern B2_PATTERN = Pattern.compile(B2);
    private static final String B3 = "a{2,3}?b{2,1000000}?c{2,4}?";
    private static final Pattern B3_PATTERN = Pattern.compile(B3);
    private static final String B4 = "(a+.*?){2,3}?(b+.*?){2,1000000}?(c+.*?){2,4}?";
    private static final Pattern B4_PATTERN = Pattern.compile(B4);

    public static String letterPattern(String letter) {
        Matcher matcherDate = DATE_PATTERN.matcher(letter);
        if (matcherDate.find()) {
            return matcherDate.group().substring(1, matcherDate.group().length() - 1);
        } else {
            return "";
        }
    }

    public static Boolean matches(String message, String type, int form) {
        //System.out.println("message=" + message +"type=" + type + "form=" + form);
        switch (form) {
            case 1:
                if (Objects.equals(type, "A")) {
                    return A1_PATTERN.matcher(message).find();
                } else {
                    return B1_PATTERN.matcher(message).find();
                }
            case 2:
                if (Objects.equals(type, "A")) {
                    return A2_PATTERN.matcher(message).find();
                } else {
                    return B2_PATTERN.matcher(message).find();
                }
            case 3:
                if (Objects.equals(type, "A")) {
                    return A3_PATTERN.matcher(message).find();
                } else {
                    return B3_PATTERN.matcher(message).find();
                }
            case 4:
                if (Objects.equals(type, "A")) {
                    return A4_PATTERN.matcher(message).find();
                } else {
                    return B4_PATTERN.matcher(message).find();
                }
            default:
                return false;

        }
    }
}