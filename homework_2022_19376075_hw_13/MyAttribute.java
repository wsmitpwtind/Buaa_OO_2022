import com.oocourse.uml1.models.common.NameableType;

public class MyAttribute {
    private final String id;
    private final String name;
    private NameableType type;

    public MyAttribute(String id, String name) {
        this.id = id;
        this.name = name;
        type = null;
    }

    public void addType(NameableType nameableType)
    {
        type = nameableType;
    }

    public String getId() {
        return id;
    }

    public NameableType getType() {
        return type;
    }
}
