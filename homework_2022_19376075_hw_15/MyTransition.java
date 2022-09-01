import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MyTransition {
    private final String id;
    private final String name;
    private final String guard;
    private MyState source;
    private MyState target;
    private String sourceId;
    private String targetId;
    private final HashMap<String, MyEvent> events;
    private final HashMap<String, MyOpaqueBehavior> opaqueBehaviors;

    public MyTransition(String id, String name, String sourceId, String targetId, String guard) {
        this.id = id;
        this.name = name;
        this.guard = guard;
        this.sourceId = sourceId;
        this.targetId = targetId;
        events = new HashMap<>();
        opaqueBehaviors = new HashMap<>();
    }

    public boolean checkGuard(MyTransition t2)
    {
        if (this.guard == null || t2.guard == null) {
            return false;
        }
        return !(this.guard.equals(t2.guard));
    }

    public boolean checkSameEventEmpty(MyTransition t2)
    {
        ArrayList<String> temp1 = new ArrayList<>();
        for (MyEvent event : this.events.values())
        {
            temp1.add(event.getName());
        }
        ArrayList<String> temp2 = new ArrayList<>();
        for (MyEvent event : t2.events.values())
        {
            temp2.add(event.getName());
        }
        Collection<String> collection = new ArrayList<>(temp1);
        collection.retainAll(temp2);
        return collection.isEmpty();
    }

    public HashMap<String, MyEvent> getEvents() {
        return events;
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
