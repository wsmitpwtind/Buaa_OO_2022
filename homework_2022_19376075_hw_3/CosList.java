import java.util.ArrayList;
import java.util.Arrays;

public class CosList extends ValueList {

    private ArrayList<String> values;

    public ArrayList<String> getValues() {
        return this.values;
    }

    public CosList(ArrayList<String> expressions) {
        this.values = expressions;
        cosSort();
    }

    public void cosSort()
    {
        String[] temp = values.toArray(new String[0]);
        Arrays.sort(temp);
        this.values = new ArrayList<>(Arrays.asList(temp));
    }
}
