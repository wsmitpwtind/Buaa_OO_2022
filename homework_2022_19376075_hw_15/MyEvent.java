public class MyEvent {
    private final String id;
    private final String name;
    private MyTransition transition;

    public MyEvent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addTransition(MyTransition myTransition) {
        transition = myTransition;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
