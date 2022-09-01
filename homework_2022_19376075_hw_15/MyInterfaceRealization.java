public class MyInterfaceRealization {
    private final String id;
    private final String name;

    public MyInterfaceRealization(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean checkName() {
        return true;
    }

    public String getId() {
        return id;
    }
}
