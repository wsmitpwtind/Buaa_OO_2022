public class RareSword extends Sword
{
    private double extraExpBonus;

    public void setExtraExpBonus(double extraExpBonus) {
        this.extraExpBonus = extraExpBonus;
    }

    public double getExtraExpBonus() {
        return this.extraExpBonus;
    }

    public void getRareSituation() {
        String situation = "";
        situation = situation + "The rareSword's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", sharpness is " + this.getSharpness();
        situation = situation + ", extraExpBonus is " + this.extraExpBonus + ".";
        System.out.println(situation);
    }

    public double useExp(double advExp)
    {
        return advExp + 10.0 + this.extraExpBonus;
    }

    public void usePrint(String advName, double advExp)
    {
        System.out.print(advName + " used " + this.getName());
        System.out.println(" and earned " + this.getSharpness() + ".");
        System.out.println(advName + " got extra exp " + this.extraExpBonus + ".");
    }
}
