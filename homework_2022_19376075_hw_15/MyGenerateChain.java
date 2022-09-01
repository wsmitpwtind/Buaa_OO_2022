import java.util.HashMap;

public class MyGenerateChain {
    public HashMap<String, String> getChain() {
        return chain;
    }

    private HashMap<String, String> chain;

    public MyGenerateChain(MyGenerateChain nowChain) {
        this.chain = new HashMap<>(nowChain.getChain());
    }

    public MyGenerateChain()
    {
        this.chain = new HashMap<>();
    }

}
