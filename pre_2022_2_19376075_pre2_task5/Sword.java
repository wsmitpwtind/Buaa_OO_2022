public class Sword extends Equipment {
    private double sharpness;

    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }

    public double getSharpness() {
        return this.sharpness;
    }

    public void getSwordSituation() {
        String situation = "";
        situation = situation + "The sword's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", sharpness is " + this.sharpness;
        situation = situation + ".";
        System.out.println(situation);
    }

    public void use(Adventurer user) {
        user.setHealth(user.getHealth() - 10);
        user.setExp(user.getExp() + 10);
        user.setMoney(user.getMoney() + this.sharpness);
        System.out.print(user.getName() + " used " + this.getName());
        System.out.println(" and earned " + this.sharpness + ".");

    }
}
