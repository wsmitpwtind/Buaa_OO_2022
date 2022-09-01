import com.oocourse.spec3.ExprInput;
import com.oocourse.spec3.ExprInputMode;

public class MainClass {

    public static void main(String[] args) {
        ExprInput scanner = new ExprInput(ExprInputMode.NormalMode);
        int cnt = scanner.getCount();
        UserDefineFunction udf = new UserDefineFunction();
        for (int i = 0; i < cnt; i++) {
            String func = scanner.readLine();
            func = stringNormalize(func);
            udf.add(func);
        }


        String input = scanner.readLine();

        input = stringNormalize(input);

        Polynomial expression = new Expression();
        //System.out.println(input);
        expression.setPoly(input,udf);
        String answer = expression.toString();
        System.out.println(answer.replaceAll("\\^", "\\*\\*"));

    }

    public static String stringNormalize(String str) {
        return str
                .replaceAll("\\s*", "")
                .replaceAll("\\*\\*", "^")
                .replaceAll("\\^\\+", "^");
    }

}
