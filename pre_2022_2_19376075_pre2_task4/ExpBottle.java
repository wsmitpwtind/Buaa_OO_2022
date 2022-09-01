public class ExpBottle extends Bottle
{
    private double expRatio;

    public void setExpRatio(double expRatio) {
        this.expRatio = expRatio;
    }

    public double getExpRatio() {
        return this.expRatio;
    }

    public void getExpBottleSituation() {
        String situation = "";
        situation = situation + "The expBottle's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", capacity is " + this.getCapacity();
        situation = situation + ", filled is " + this.getFilled();
        situation = situation + ", expRatio is " + this.expRatio + ".";
        System.out.println(situation);
    }

    public double useHealth() {
        if (this.getFilled())
        {
            this.setPrice(this.getPrice() / 10);
            return (this.getCapacity() / 10);
        }
        else
        {
            return 0;
        }
    }

    public double useExp(double advExp) {
        if (this.getFilled())
        {
            return advExp * this.expRatio;
        }
        else
        {
            return advExp;
        }
    }

    public void usePrint(String advName, double advExp)
    {
        if (this.getFilled())
        {
            System.out.print(advName + " drank " + this.getName());
            System.out.println(" and recovered " + (this.getCapacity() / 10) + ".");
            System.out.println(advName + "'s exp became " + advExp * this.expRatio + ".");
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
