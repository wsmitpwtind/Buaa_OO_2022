import java.math.BigInteger;

public class HealingPotion extends Bottle {
    private double efficiency;

    //来自 ValueBody
    public void use(Adventurer user) {
        if (this.getFilled()) {
            this.setPrice(this.getPrice().divide(BigInteger.valueOf(10)));
            user.setHealth(user.getHealth() + (this.getCapacity() * (0.1 + this.efficiency)));
            this.setFilled(false);
            System.out.print(user.getName() + " drank " + this.getName() + " and recovered ");
            System.out.println((this.getCapacity() / 10) + ".");
            System.out.print(user.getName() + " recovered extra ");
            System.out.println(this.getCapacity() * this.efficiency + ".");
        } else {
            System.out.println("Failed to use " + this.getName() + " because it is empty.");
        }
    }

    //个人信息
    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public double getEfficiency() {
        return this.efficiency;
    }

    //打印信息
    public void getHealingPotionSituation() {
        String situation = "";
        situation = situation + "The healingPotion's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", capacity is " + this.getCapacity();
        situation = situation + ", filled is " + this.getFilled();
        situation = situation + ", efficiency is " + this.efficiency + ".";
        System.out.println(situation);
    }

}
