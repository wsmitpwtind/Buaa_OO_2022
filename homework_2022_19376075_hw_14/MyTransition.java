import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTransition {
    private final String id;
    private final String name;
    private MyState source;
    private MyState target;
    private String sourceId;
    private String targetId;
    private final HashMap<String, MyEvent> events;
    private final HashMap<String, MyOpaqueBehavior> opaqueBehaviors;

    public MyTransition(String id, String name, String sourceId, String targetId) {
        this.id = id;
        this.name = name;
        this.sourceId = sourceId;
        this.targetId = targetId;
        events = new HashMap<>();
        opaqueBehaviors = new HashMap<>();
    }

    public void addSource(MyState myState) {
        source = myState;
    }

    public void addTarget(MyState myState) {
        target = myState;
    }

    public String getId() {
        return id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public MyState getTarget() {
        return target;
    }

    public void addEvent(MyEvent myEvent) {
        myEvent.addTransition(this);
        events.put(myEvent.getId(), myEvent);
    }

    public void addOpaqueBehavior(MyOpaqueBehavior myOpaqueBehavior) {
        myOpaqueBehavior.addTransition(this);
        opaqueBehaviors.put(myOpaqueBehavior.getId(), myOpaqueBehavior);
    }

    public List<String> getEventsName()
    {
        List<String> answer = new ArrayList<>();
        for (MyEvent myEvent : events.values())
        {
            answer.add(myEvent.getName());
        }
        return answer;
    }
}
