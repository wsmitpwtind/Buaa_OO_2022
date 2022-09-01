import java.util.ArrayList;
import java.util.Arrays;

public class SinList extends ValueList {

    private ArrayList<String> values;

    public ArrayList<String> getValues() {
        return this.values;
    }

    public SinList(ArrayList<String> expressions) {
        this.values = expressions;
        sinSort();
    }

    public void sinSort()
    {
        String[] temp = values.toArray(new String[0]);
        Arrays.sort(temp);
        this.values = new ArrayList<>(Arrays.asList(temp));
    }

}
