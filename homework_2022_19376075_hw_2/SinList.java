import java.util.Arrays;

public class SinList extends ValueList {

    private String[] values;

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public SinList(String[] values) {
        this.values = values;
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = deleteUseLess(this.values[i]);
        }
    }

    public void sinSort() {
        Arrays.sort(this.values);
    }

    private String deleteUseLess(String string) {
        String answer = string;
        int temp = 0;
        boolean y = false;
        if (answer.length() <= 2) {
            return answer;
        }
        for (int i = 0; i < string.length(); i++) {
            if (answer.charAt(i) == '(') {
                temp++;
            }
            if (answer.charAt(i) == ')') {
                temp--;
            }
            if (i == string.length() - 1) {
                y = true;
            }
            if (temp == 0) {
                break;
            }
        }
        if (temp == 0 && y) {
            answer = answer.substring(1, string.length() - 1);
        }
        return answer;
    }
}
