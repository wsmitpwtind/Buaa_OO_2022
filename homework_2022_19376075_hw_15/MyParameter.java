import com.oocourse.uml3.models.common.Direction;
import com.oocourse.uml3.models.common.NameableType;

public class MyParameter {
    private final String id;
    private final String name;
    private Direction direction;
    private NameableType type;

    public MyParameter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean checkName() {
        if (direction == Direction.RETURN) {
            return true;
        } else {
            return (name != null) && (name.trim().length() != 0);
        }
    }

    public String getId() {
        return id;
    }

    public void addDirection(Direction direction)
    {
        this.direction = direction;
    }

    public void addType(NameableType type)
    {
        this.type = type;
    }

    public Direction getDirection() {
        return direction;
    }

    public NameableType getType() {
        return type;
    }
}
