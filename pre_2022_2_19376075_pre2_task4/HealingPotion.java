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

    public double useHealth() {
        if (this.getFilled())
        {
            this.setPrice(this.getPrice() / 10);
            return (this.getCapacity() * (0.1 + this.efficiency));
        }
        else
        {
            return 0;
        }
    }

    public void usePrint(String advName, double advExp)
    {
        if (this.getFilled())
        {
            System.out.print(advName + " drank " + this.getName());
            System.out.println(" and recovered " + (this.getCapacity() / 10) + ".");
            System.out.print(advName + " recovered extra ");
            System.out.println(this.getCapacity() * this.efficiency + ".");
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
