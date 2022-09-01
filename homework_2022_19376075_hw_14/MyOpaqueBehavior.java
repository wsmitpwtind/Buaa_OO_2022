public class MyOpaqueBehavior {
    private final String id;
    private final String name;
    private MyTransition transition;

    public MyOpaqueBehavior(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addTransition(MyTransition myTransition) {
        transition = myTransition;
    }

    public String getId() {
        return id;
    }
}
