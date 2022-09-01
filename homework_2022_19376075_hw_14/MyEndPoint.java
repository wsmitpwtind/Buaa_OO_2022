import java.util.HashMap;

public class MyEndPoint {
    private final String id;
    private final String name;
    private MyInteraction interaction;
    private final HashMap<String, MyMessage> asSource;
    private final HashMap<String, MyMessage> asTarget;

    public MyEndPoint(String id, String name) {
        this.id = id;
        this.name = name;
        asSource = new HashMap<>();
        asTarget = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void addInteraction(MyInteraction myInteraction) {
        interaction = myInteraction;
    }

    public void addaAsSource(MyMessage myMessage) {
        asSource.put(myMessage.getId(), myMessage);
    }

    public void addaAsTarget(MyMessage myMessage) {
        asTarget.put(myMessage.getId(), myMessage);
    }
}
