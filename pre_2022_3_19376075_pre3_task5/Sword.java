public class Sword extends Equipment {
    private double sharpness;

    //来自 ValueBody
    public void use(Adventurer user) {
        user.setHealth(user.getHealth() - 10);
        user.setExp(user.getExp() + 10);
        user.setMoney(user.getMoney() + this.sharpness);
        System.out.print(user.getName() + " used " + this.getName());
        System.out.println(" and earned " + this.sharpness + ".");

    }

    //个人信息
    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }

    public double getSharpness() {
        return this.sharpness;
    }

    //打印信息
    public void getSwordSituation() {
        String situation = "";
        situation = situation + "The sword's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", sharpness is " + this.sharpness;
        situation = situation + ".";
        System.out.println(situation);
    }
}
