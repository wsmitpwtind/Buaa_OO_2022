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
}
