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

    public void use(Adventurer user) {
        user.setHealth(user.getHealth() - 10);
        user.setExp(user.getExp() + 10 + this.extraExpBonus);
        user.setMoney(user.getMoney() + this.getSharpness());
        System.out.print(user.getName() + " used " + this.getName());
        System.out.println(" and earned " + this.getSharpness() + ".");
        System.out.println(user.getName() + " got extra exp " + this.extraExpBonus + ".");
    }
}
