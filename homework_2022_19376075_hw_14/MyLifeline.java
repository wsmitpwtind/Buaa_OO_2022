import java.util.HashMap;

public class MyLifeline {
    private final String id;
    private final String name;
    private MyInteraction interaction;
    private MyAttribute attribute;
    private final HashMap<String, MyMessage> asSource;
    private final HashMap<String, MyMessage> asTarget;

    public MyLifeline(String id, String name) {
        this.id = id;
        this.name = name;
        asSource = new HashMap<>();
        asTarget = new HashMap<>();
    }

    public int receiveFoundCount() {
        int answer = 0;
        for (MyMessage myMessage : asTarget.values()) {
            if (!myMessage.isFromLifeLine()) {
                answer++;
            }
        }
        return answer;
    }

    public int sendLostCount() {
        int answer = 0;
        for (MyMessage myMessage : asSource.values()) {
            if (!myMessage.isToLifeLine()) {
                answer++;
            }
        }
        return answer;
    }

    public boolean checkCreatExit() {
        for (MyMessage myMessage : asTarget.values()) {
            if (myMessage.isCreat()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCreatDuplicated() {
        boolean one = false;
        for (MyMessage myMessage : asTarget.values()) {
            if (myMessage.isCreat() && !one) {
                one = true;
            } else if (myMessage.isCreat() && one) {
                return true;
            }
        }
        return false;
    }

    public String findCreatorId() {
        for (MyMessage myMessage : asTarget.values()) {
            if (myMessage.isCreat()) {
                return myMessage.getSourceId();
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addInteraction(MyInteraction myInteraction) {
        interaction = myInteraction;
    }

    public void addAttribute(MyAttribute myAttribute) {
        attribute = myAttribute;
    }

    public void addaAsSource(MyMessage myMessage) {
        asSource.put(myMessage.getId(), myMessage);
    }

    public void addaAsTarget(MyMessage myMessage) {
        asTarget.put(myMessage.getId(), myMessage);
    }
}
