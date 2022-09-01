import java.util.HashMap;

public class MyState {
    private final String id;
    private final String name;

    private final HashMap<String, MyTransition> transitions;

    public MyState(String id, String name) {
        this.id = id;
        this.name = name;
        transitions = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addTransition(MyTransition myTransition) {
        transitions.put(myTransition.getId(), myTransition);
    }

    public HashMap<String, MyTransition> getTransitions() {
        return transitions;
    }
}
