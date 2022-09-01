public class MyStateMachine {
    private final String id;
    private final String name;
    private MyRegion region;

    public MyStateMachine(String id, String name) {
        this.id = id;
        this.name = name;
        region = null;
    }

    public int getStatesCount() {
        return region.getStatesCount();
    }

    public void addRegion(MyRegion myRegion)
    {
        region = myRegion;
        myRegion.addStateMachine(this);
    }

    public MyRegion getRegion()
    {
        return region;
    }
}
