public class Bottle {
    private int id;
    private String name;
    private long price;
    private double capacity;
    private boolean filled;

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public long getPrice()
    {
        return this.price;
    }

    public double getCapacity()
    {
        return this.capacity;
    }

    public boolean getFilled()
    {
        return this.filled;
    }

    public String getSituation()
    {
        String situation = "";
        situation = situation + "The bottle's id is " + this.id + ", name is ";
        situation = situation + this.name + ", capacity is " + this.capacity;
        situation = situation + ", filled is " + this.filled + ".";
        return situation;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setCapacity(double capacity)
    {
        this.capacity = capacity;
    }

    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }

}
