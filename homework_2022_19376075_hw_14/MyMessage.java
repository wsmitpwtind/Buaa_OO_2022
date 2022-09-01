import com.oocourse.uml2.models.common.MessageSort;

public class MyMessage {
    private final String id;
    private final String name;
    private MyInteraction interaction;
    private String sourceId;
    private boolean fromLifeLine;
    private String targetId;
    private boolean toLifeLine;
    private MessageSort messageSort;

    public MyMessage(String id, String name, MessageSort messageSort) {
        this.id = id;
        this.name = name;
        this.messageSort = messageSort;
    }

    public String getId() {
        return id;
    }

    public void addInteraction(MyInteraction myInteraction) {
        interaction = myInteraction;
    }

    public boolean isCreat()
    {
        return messageSort.getOriginalString().equals("createMessage");
    }

    public void addSourceTargetId(String sourceId, String targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public void addFromLifeLine(boolean type) {
        this.fromLifeLine = type;
    }

    public void addToLifeLine(boolean type) {
        this.toLifeLine = type;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public boolean isFromLifeLine() {
        return fromLifeLine;
    }

    public boolean isToLifeLine() {
        return toLifeLine;
    }
}
