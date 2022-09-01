import java.util.ArrayList;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;

public class Adventurer implements ValueBody {
    private int id;
    private String name;
    private List<ValueBody> valueBodies;
    private double health;
    private double exp;
    private double money;
    private BigInteger price = new BigInteger("0");

    public void setAdventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.valueBodies = new ArrayList<ValueBody>();
        this.health = 100.0;
        this.exp = 0.0;
        this.money = 0.0;
    }

    public void createEquipment(int equType, List vars) {
        switch (equType) {
            case 1:
                Bottle bottle = new Bottle();
                bottle.setId((Integer) vars.get(0));
                bottle.setName((String) vars.get(1));
                bottle.setPrice((BigInteger) vars.get(2));
                bottle.setCapacity((Double) vars.get(3));
                valueBodies.add(bottle);
                break;
            case 2:
                HealingPotion healingPotion = new HealingPotion();
                healingPotion.setId((Integer) vars.get(0));
                healingPotion.setName((String) vars.get(1));
                healingPotion.setPrice((BigInteger) vars.get(2));
                healingPotion.setCapacity((Double) vars.get(3));
                healingPotion.setEfficiency((Double) vars.get(4));
                valueBodies.add(healingPotion);
                break;
            case 3:
                ExpBottle expBottle = new ExpBottle();
                expBottle.setId((Integer) vars.get(0));
                expBottle.setName((String) vars.get(1));
                expBottle.setPrice((BigInteger) vars.get(2));
                expBottle.setCapacity((Double) vars.get(3));
                expBottle.setExpRatio((Double) vars.get(4));
                valueBodies.add(expBottle);
                break;
            case 4:
                Sword sword = new Sword();
                sword.setId((Integer) vars.get(0));
                sword.setName((String) vars.get(1));
                sword.setPrice((BigInteger) vars.get(2));
                sword.setSharpness((Double) vars.get(3));
                valueBodies.add(sword);
                break;
            case 5:
                RareSword rareSword = new RareSword();
                rareSword.setId((Integer) vars.get(0));
                rareSword.setName((String) vars.get(1));
                rareSword.setPrice((BigInteger) vars.get(2));
                rareSword.setSharpness((Double) vars.get(3));
                rareSword.setExtraExpBonus((Double) vars.get(4));
                valueBodies.add(rareSword);
                break;
            case 6:
                EpicSword epicSword = new EpicSword();
                epicSword.setId((Integer) vars.get(0));
                epicSword.setName((String) vars.get(1));
                epicSword.setPrice((BigInteger) vars.get(2));
                epicSword.setSharpness((Double) vars.get(3));
                epicSword.setEvolveRatio((Double) vars.get(4));
                valueBodies.add(epicSword);
                break;
            default:
                break;
        }
    }

    public void createEmploy(Adventurer employ) {
        valueBodies.add(employ);
    }

    public void deleteValueBody(int equId) {
        for (ValueBody item : valueBodies) {
            if (item.getId() == equId) {
                valueBodies.remove(item);
                break;
            }
        }
    }

    public BigInteger sumValueBodyPrice() {
        BigInteger sumPrice = new BigInteger("0");
        for (ValueBody item : valueBodies) {
            sumPrice = sumPrice.add(item.getPrice());
        }
        return sumPrice;
    }

    public BigInteger maxValueBodyPrice() {
        BigInteger maxPrice = new BigInteger("0");
        for (ValueBody item : valueBodies) {
            maxPrice = maxPrice.max(item.getPrice());
        }
        return maxPrice;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getHealth() {
        return this.health;
    }

    public double getExp() {
        return this.exp;
    }

    public double getMoney() {
        return this.money;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public BigInteger getPrice() {
        BigInteger sumPrice = new BigInteger("0");
        for (ValueBody item : valueBodies) {
            sumPrice = sumPrice.add(item.getPrice());
        }
        this.price = sumPrice;
        return sumPrice;
    }

    public int getEquipmentNum() {
        return this.valueBodies.size();
    }

    public void printEquipment(int equId) {
        for (ValueBody item : valueBodies) {
            if (item.getId() == equId) {
                if (item instanceof RareSword) {
                    ((RareSword) item).getRareSituation();
                } else if (item instanceof EpicSword) {
                    ((EpicSword) item).getEpicSituation();
                } else if (item instanceof Sword) {
                    ((Sword) item).getSwordSituation();
                } else if (item instanceof HealingPotion) {
                    ((HealingPotion) item).getHealingPotionSituation();
                } else if (item instanceof ExpBottle) {
                    ((ExpBottle) item).getExpBottleSituation();
                } else if (item instanceof Bottle) {
                    ((Bottle) item).getBottleSituation();
                } else if (item instanceof Adventurer) {
                    ((Adventurer) item).getAdventurerSituation();
                }
                break;
            }

        }
    }

    public void use(Adventurer user) {
        useSort();
        for (ValueBody item : valueBodies) {
            item.use(user);
        }
    }

    public void printAdventurer() {
        System.out.print("The adventurer's id is " + this.id);
        System.out.print(", name is " + this.name + ", health is ");
        System.out.print(this.health + ", exp is " + this.exp);
        System.out.println(", money is " + this.money + ".");
    }

    public void useSort() {
        valueBodies.sort(new Comparator<ValueBody>() {
            @Override

            public int compare(ValueBody o1, ValueBody o2) {
                if (o1.getPrice().equals(o2.getPrice())) {
                    return o2.getId() - o1.getId();
                } else {
                    if (o2.getPrice().max(o1.getPrice()).equals(o2.getPrice())) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }

    public void getAdventurerSituation() {
        String situation = "";
        situation = situation + "The adventurer's id is " + this.getId() + ", name is ";
        situation = situation + this.getName() + ", health is " + this.health + ", exp is ";
        situation = situation + this.exp + ", money is " + this.money + ".";
        System.out.println(situation);
    }
}


