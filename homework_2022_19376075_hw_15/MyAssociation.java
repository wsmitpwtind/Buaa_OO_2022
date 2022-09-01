public class MyAssociation {
    private final String id;
    private final String name;

    public MyAssociationEnd getEnd1() {
        return end1;
    }

    public MyAssociationEnd getEnd2() {
        return end2;
    }

    private MyAssociationEnd end1;
    private MyAssociationEnd end2;

    public MyAssociation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean checkName() {
        return true;
    }

    public void addEnd1(MyAssociationEnd myAssociationEnd) {
        myAssociationEnd.addAssociation(this);
        end1 = myAssociationEnd;
    }

    public void addEnd2(MyAssociationEnd myAssociationEnd) {
        myAssociationEnd.addAssociation(this);
        end2 = myAssociationEnd;
    }

    public String getId() {
        return id;
    }
}
