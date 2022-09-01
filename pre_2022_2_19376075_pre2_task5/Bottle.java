import java.math.BigInteger;

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
        //
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

    public void use(Adventurer user)
    {
        if (this.filled)
        {
            this.setPrice(this.getPrice().divide(BigInteger.valueOf(10)));
            user.setHealth(user.getHealth() + (this.capacity / 10));
            this.setFilled(false);
            System.out.print(user.getName() + " drank " + this.getName() + " and recovered ");
            System.out.println((this.capacity / 10) + ".");
        }
        else
        {
            System.out.println("Failed to use " + this.getName() + " because it is empty.");
        }
    }

}