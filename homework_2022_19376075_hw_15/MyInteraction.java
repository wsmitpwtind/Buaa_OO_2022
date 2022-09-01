import java.util.HashMap;
import java.util.Objects;

public class MyInteraction {
    private final String id;
    private final String name;

    public MyCollaboration getCollaboration() {
        return collaboration;
    }

    private MyCollaboration collaboration;
    private final HashMap<String, MyLifeline> lifelines;
    private final HashMap<String, MyEndPoint> endPoints;
    private final HashMap<String, MyMessage> messages;

    public MyInteraction(String id, String name) {
        this.id = id;
        this.name = name;
        lifelines = new HashMap<>();
        endPoints = new HashMap<>();
        messages = new HashMap<>();
    }

    public boolean checkLifeLineExit(String name) {
        for (MyLifeline myLifeline : lifelines.values()) {
            if (Objects.equals(myLifeline.getName(), name)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLifeLineDuplicated(String name) {
        boolean one = false;
        for (MyLifeline myLifeline : lifelines.values()) {
            if (Objects.equals(myLifeline.getName(), name) && !one) {
                one = true;
            } else if (Objects.equals(myLifeline.getName(), name) && one) {
                return true;
            }
        }
        return false;
    }

    public MyLifeline getLifeLine(String name) {
        for (MyLifeline myLifeline : lifelines.values()) {
            if (Objects.equals(myLifeline.getName(), name)) {
                return myLifeline;
            }
        }
        return null;
    }

    public HashMap<String, MyLifeline> getLifeLines() {
        return lifelines;
    }

    public int getParticipantCount() {
        return lifelines.size();
    }

    public String getId() {
        return id;
    }

    public void addCollaboration(MyCollaboration myCollaboration) {
        collaboration = myCollaboration;
    }

    public void addLifeLine(MyLifeline myLifeline) {
        lifelines.put(myLifeline.getId(), myLifeline);
        myLifeline.addInteraction(this);
    }

    public void addEndPoint(MyEndPoint myEndPoint) {
        endPoints.put(myEndPoint.getId(), myEndPoint);
        myEndPoint.addInteraction(this);
    }

    public boolean addMessage(MyMessage myMessage) {
        messages.put(myMessage.getId(), myMessage);
        myMessage.addInteraction(this);
        if (lifelines.containsKey(myMessage.getSourceId())) {
            lifelines.get(myMessage.getSourceId()).addaAsSource(myMessage);
            myMessage.addFromLifeLine(true);
        } else if (endPoints.containsKey(myMessage.getSourceId())) {
            endPoints.get(myMessage.getSourceId()).addaAsSource(myMessage);
            myMessage.addFromLifeLine(false);
        }
        if (lifelines.containsKey(myMessage.getTargetId())) {
            lifelines.get(myMessage.getTargetId()).addaAsTarget(myMessage);
            if (lifelines.get(myMessage.getTargetId()).isAlreadyDeleted()) {
                return false;
            }
            if (myMessage.isDelete()) {
                lifelines.get(myMessage.getTargetId()).setAlreadyDeleted(true);
            }
            myMessage.addToLifeLine(true);
        } else if (endPoints.containsKey(myMessage.getTargetId())) {
            endPoints.get(myMessage.getTargetId()).addaAsTarget(myMessage);
            myMessage.addToLifeLine(false);
        }
        return true;
    }
}
