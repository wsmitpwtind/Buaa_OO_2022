import java.util.Objects;

public class MyAssociationEnd {
    private final String id;

    public String getName() {
        return name;
    }

    public String getAnotherName() {
        MyAssociation myAssociation = association;
        if (Objects.equals(myAssociation.getEnd1().id, id)) {
            return association.getEnd2().name;
        } else {
            return association.getEnd1().name;
        }
    }

    private final String name;
    private MyAssociation association;

    public MyAssociationEnd(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean checkName() {
        return true;
    }

    public void addAssociation(MyAssociation myAssociation) {
        association = myAssociation;
    }

    public String getId() {
        return id;
    }
}
