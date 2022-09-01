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

    public double useMoney() {
        double money = this.getSharpness();
        this.setSharpness(this.getSharpness() * this.evolveRatio);
        return money;
    }

    public void usePrint(String advName, double advExp)
    {
        System.out.print(advName + " used " + this.getName());
        System.out.println(" and earned " + this.getSharpness() + ".");
        System.out.print(this.getName() + "'s sharpness became ");
        System.out.println(this.getSharpness() * this.evolveRatio + ".");
    }
}