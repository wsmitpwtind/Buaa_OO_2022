public class Bottle extends Equipment
{
    private double capacity;
    private boolean filled = true;

    public double getCapacity() {
        return this.capacity;
    }

    public boolean getFilled() {
        return this.filled;
    }

    public void getBottleSituation() {
        String situation = "";
        situation = situation + "The bottle's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", capacity is " + this.capacity;
        situation = situation + ", filled is " + this.filled + ".";
        System.out.println(situation);
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public double useHealth() {
        if (this.filled)
        {
            this.setPrice(this.getPrice() / 10);
            return (this.capacity / 10);
        }
        else
        {
            return 0;
        }
    }

    public void usePrint(String advName, double advExp)
    {
        if (this.filled)
        {
            System.out.print(advName + " drank " + this.getName() + " and recovered ");
            System.out.println((this.capacity / 10) + ".");
        }
        else
        {
            System.out.println("Failed to use " + this.getName() + " because it is empty.");
        }
    }

    public void used()
    {
        this.setFilled(false);
    }

}