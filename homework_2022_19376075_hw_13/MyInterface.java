import java.util.ArrayList;
import java.util.HashMap;

public class MyInterface {
    private final String id;
    private final String name;
    private final HashMap<String, MyInterface> fathers;
    private final HashMap<String, MyInterface> sons;
    private final HashMap<String, MyAttribute> attributes;

    public MyInterface(String id, String name) {
        this.id = id;
        this.name = name;
        fathers = new HashMap<>();
        sons = new HashMap<>();
        attributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void addAttribute(MyAttribute myAttribute) {
        attributes.put(myAttribute.getId(), myAttribute);
    }

    public void addFamily(MyInterface myInterface, String relationship) {
        switch (relationship) {
            case "father":
                fathers.put(myInterface.id, myInterface);
                return;
            case "son":
                sons.put(myInterface.id, myInterface);
                return;
            default:
        }
    }

    public ArrayList<String> getClassImplementInterfaceListFather() {
        ArrayList<String> ans = new ArrayList<>();
        ans.add(name);

        for (MyInterface myInterface : fathers.values()) {
            ans.addAll(myInterface.getClassImplementInterfaceListFather());
        }

        return ans;
    }
}
