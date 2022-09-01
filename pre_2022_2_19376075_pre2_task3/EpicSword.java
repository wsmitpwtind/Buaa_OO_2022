public class EpicSword extends Sword
{
    private double evolveRatio;

    public void setEvolveRatio(double evolveRatio) {
        this.evolveRatio = evolveRatio;
    }

    public double getEvolveRatio() {
        return this.evolveRatio;
    }

    public void getEpicSituation() {
        String situation = "";
        situation = situation + "The epicSword's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", sharpness is " + this.getSharpness();
        situation = situation + ", evolveRatio is " + this.evolveRatio + ".";
        System.out.println(situation);
    }
}