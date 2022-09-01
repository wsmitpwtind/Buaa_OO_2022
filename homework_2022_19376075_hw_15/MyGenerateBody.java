import java.util.HashMap;

public class MyGenerateBody {
    private final String id;
    private final String name;
    private HashMap<String, MyGenerateBody> fathers;

    public boolean isSameFather() {
        return sameFather;
    }

    private boolean sameFather;

    public MyGenerateBody(String id, String name) {
        this.id = id;
        this.name = name;
        fathers = new HashMap<>();
        sameFather = false;
    }

    public String getId() {
        return id;
    }

    public void addFathers(MyGenerateBody myGenerateBody) {
        if (fathers.containsKey(myGenerateBody.getId())) {
            sameFather = true;
        }
        fathers.put(myGenerateBody.getId(), myGenerateBody);
    }

    public String getName() {
        return name;
    }

    public HashMap<String, MyGenerateBody> getFathers() {
        return fathers;
    }
}
