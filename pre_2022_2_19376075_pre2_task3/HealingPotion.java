public class HealingPotion extends Bottle
{
    private double efficiency;

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public double getEfficiency() {
        return this.efficiency;
    }

    public void getHealingPotionSituation() {
        String situation = "";
        situation = situation + "The healingPotion's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", capacity is " + this.getCapacity();
        situation = situation + ", filled is " + this.getFilled();
        situation = situation + ", efficiency is " + this.efficiency + ".";
        System.out.println(situation);
    }
}
