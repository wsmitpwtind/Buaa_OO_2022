import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MyRegion {
    private final String id;
    private final String name;
    private MyStateMachine stateMachine;
    private MyState pseudoState;
    private final HashMap<String, MyState> states;
    private final HashMap<String, MyState> finialStates;
    private final HashMap<String, MyTransition> transitions;

    private HashMap<String, MyState> visited;
    private int ways;

    public MyRegion(String id, String name) {
        this.id = id;
        this.name = name;
        stateMachine = null;
        states = new HashMap<>();
        finialStates = new HashMap<>();
        transitions = new HashMap<>();
        visited = new HashMap<>();
        ways = 0;
    }

    public boolean checkStataGuard()
    {
        if (!pseudoState.checkSameTrigger())
        {
            return false;
        }
        for (MyState myState : states.values()) {
            if (!myState.checkSameTrigger())
            {
                return false;
            }
        }
        for (MyState myState : finialStates.values()) {
            if (!myState.checkSameTrigger())
            {
                return false;
            }
        }
        return true;
    }

    public boolean checkFinialState()
    {
        for (MyState myState : finialStates.values())
        {
            for (MyTransition myTransition : transitions.values())
            {
                if (Objects.equals(myTransition.getSourceId(), myState.getId()))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkStateExit(String name) {
        for (MyState myState : states.values()) {
            if (Objects.equals(myState.getName(), name)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkStateDuplicated(String name) {
        boolean one = false;
        for (MyState myState : states.values()) {
            if (Objects.equals(myState.getName(), name) && !one) {
                one = true;
            } else if (Objects.equals(myState.getName(), name) && one) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTransition(String s1, String s2) {
        MyState myState = null;
        for (MyState state : states.values()) {
            if (Objects.equals(state.getName(), s1)) {
                myState = state;
            }
        }
        assert myState != null;
        for (MyTransition transition : myState.getTransitions().values()) {
            MyState tar = transition.getTarget();
            if (Objects.equals(tar.getName(), s2)) {
                return true;
            }
        }
        return false;
    }

    private void dps(String id) {
        if (finialStates.containsKey(id)) {
            ways++;
        } else {
            MyState myState = states.get(id);
            for (MyTransition myTransition : myState.getTransitions().values()) {
                if (!visited.containsKey(myTransition.getTargetId())) {
                    visited.put(myTransition.getTargetId(), myTransition.getTarget());
                    dps(myTransition.getTargetId());
                }
            }
        }
    }

    private int dpsMain() {
        ways = 0;
        for (MyTransition myTransition : pseudoState.getTransitions().values()) {
            if (!visited.containsKey(myTransition.getTargetId())) {
                visited.put(myTransition.getTargetId(), myTransition.getTarget());
                dps(myTransition.getTargetId());
            }
        }
        return ways;
    }

    public boolean checkCriticalState(String name) {
        MyState myState = null;
        for (MyState state : states.values()) {
            if (Objects.equals(state.getName(), name)) {
                myState = state;
            }
        }
        assert myState != null;
        visited = new HashMap<>();
        if (Objects.equals(pseudoState.getId(), myState.getId())) {
            return false;
        } else if (finialStates.containsKey(myState.getId())) {
            return false;
        } else if (finialStates.isEmpty()) {
            return false;
        } else if (dpsMain() == 0) {
            return false;
        } else {
            visited = new HashMap<>();
            visited.put(myState.getId(), myState);
            return dpsMain() == 0;
        }
    }

    public List<String> getEvents(String s1, String s2)
    {
        MyState myState = null;
        for (MyState state : states.values()) {
            if (Objects.equals(state.getName(), s1)) {
                myState = state;
            }
        }
        assert myState != null;
        List<String> answer = new ArrayList<>();
        for (MyTransition transition : myState.getTransitions().values()) {
            MyState tar = transition.getTarget();
            if (Objects.equals(tar.getName(), s2)) {
                answer.addAll(transition.getEventsName());
            }
        }
        return answer;
    }

    public int getStatesCount() {
        return 1 + states.size() + finialStates.size();
    }

    public void addStateMachine(MyStateMachine myStateMachine) {
        stateMachine = myStateMachine;
    }

    public void addPseudoState(MyState myState) {
        pseudoState = myState;
    }

    public void addState(MyState myState) {
        states.put(myState.getId(), myState);
    }

    public void addFinialState(MyState myState) {
        finialStates.put(myState.getId(), myState);
    }

    private MyState stateFind(String id) {
        if (Objects.equals(pseudoState.getId(), id)) {
            return pseudoState;
        } else if (states.containsKey(id)) {
            return states.get(id);
        } else {
            return finialStates.get(id);
        }
    }

    public void addTransition(MyTransition myTransition) {
        myTransition.addSource(stateFind(myTransition.getSourceId()));
        myTransition.addTarget(stateFind(myTransition.getTargetId()));
        stateFind(myTransition.getSourceId()).addTransition(myTransition);
        transitions.put(myTransition.getId(), myTransition);
    }
}
