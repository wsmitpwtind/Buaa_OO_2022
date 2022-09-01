import java.util.HashMap;

public class MyCollaboration {
    public String getId() {
        return id;
    }

    private final String id;
    private final String name;
    private final HashMap<String, MyInteraction> interactions;
    private final HashMap<String, MyAttribute> attributes;

    public MyCollaboration(String id, String name) {
        this.id = id;
        this.name = name;
        interactions = new HashMap<>();
        attributes = new HashMap<>();
    }

    public void addInteraction(MyInteraction myInteraction)
    {
        interactions.put(myInteraction.getId(), myInteraction);
        myInteraction.addCollaboration(this);
    }

    public void addAttribute(MyAttribute myAttribute) {
        attributes.put(myAttribute.getId(), myAttribute);
    }

}
