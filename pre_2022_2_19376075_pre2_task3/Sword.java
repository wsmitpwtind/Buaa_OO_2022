public class Sword extends Equipment
{
    private double sharpness;

    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }

    public double getSharpness() {
        return this.sharpness;
    }

    public void getSwordSituation() {
        String situation = "";
        situation = situation + "The sword's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", sharpness is " + this.sharpness;
        situation = situation + ".";
        System.out.println(situation);
    }
}
