public class MyGeneralization {
    private final String id;
    private final String name;

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    private String source;
    private String target;

    public MyGeneralization(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addSource(String source)
    {
        this.source = source;
    }

    public void addTarget(String target)
    {
        this.target = target;
    }

    public boolean checkName() {
        return true;
    }

    public String getId() {
        return id;
    }
}
