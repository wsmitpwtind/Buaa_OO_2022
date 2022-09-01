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
}
