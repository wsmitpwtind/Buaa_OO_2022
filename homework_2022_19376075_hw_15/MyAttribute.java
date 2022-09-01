import com.oocourse.uml3.models.common.NameableType;
import com.oocourse.uml3.models.common.Visibility;

public class MyAttribute {
    private final String id;

    public String getParentId() {
        return parentId;
    }

    private final String parentId;
    private final String name;
    private NameableType type;
    private Visibility visibility;

    public MyAttribute(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        type = null;
        this.parentId = parentId;
    }

    public void addVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public boolean checkVisibility() {
        return visibility == Visibility.PUBLIC;
    }

    public boolean checkName() {
        return (name != null) && (!name.matches("[ \t]*"));
    }

    public void addType(NameableType nameableType)
    {
        type = nameableType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public NameableType getType() {
        return type;
    }
}
