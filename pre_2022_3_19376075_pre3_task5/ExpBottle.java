import java.math.BigInteger;

public class ExpBottle extends Bottle {
    private double expRatio;

    //来自 ValueBody
    public void use(Adventurer user) {
        if (this.getFilled()) {
            this.setPrice(this.getPrice().divide(BigInteger.valueOf(10)));
            user.setHealth(user.getHealth() + (this.getCapacity() / 10));
            user.setExp(user.getExp() * this.expRatio);
            this.setFilled(false);
            System.out.print(user.getName() + " drank " + this.getName() + " and recovered ");
            System.out.println((this.getCapacity() / 10) + ".");
            System.out.println(user.getName() + "'s exp became " + user.getExp() + ".");
        } else {
            System.out.println("Failed to use " + this.getName() + " because it is empty.");
        }
    }

    //个人信息
    public void setExpRatio(double expRatio) {
        this.expRatio = expRatio;
    }

    public double getExpRatio() {
        return this.expRatio;
    }

    //打印信息
    public void getExpBottleSituation() {
        String situation = "";
        situation = situation + "The expBottle's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", capacity is " + this.getCapacity();
        situation = situation + ", filled is " + this.getFilled();
        situation = situation + ", expRatio is " + this.expRatio + ".";
        System.out.println(situation);
    }

}
