import java.util.HashMap;
import java.util.Objects;

public class MyState {
    private final String id;
    private final String name;

    private final HashMap<String, MyTransition> transitions;

    public MyState(String id, String name) {
        this.id = id;
        this.name = name;
        transitions = new HashMap<>();
    }

    public boolean checkSameTrigger()
    {
        for (MyTransition t1 : transitions.values())
        {
            for (MyTransition t2 : transitions.values())
            {
                if (!Objects.equals(t1.getId(), t2.getId()))
                {
                    if (!t1.checkSameEventEmpty(t2))
                    {
                        if (!t1.checkGuard(t2))
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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
