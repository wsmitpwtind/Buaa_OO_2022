import java.util.ArrayList;
import java.math.BigInteger;
import java.util.List;

public class Adventurer {
    private int id;
    private String name;
    private List equipments;

    public void setAdventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.equipments = new ArrayList();
    }

    public void createEquipment(int equType, List vars) {
        switch (equType) {
            case 1:
                Bottle bottle = new Bottle();
                bottle.setId((Integer) vars.get(0));
                bottle.setName((String) vars.get(1));
                bottle.setPrice((Long) vars.get(2));
                bottle.setCapacity((Double) vars.get(3));
                equipments.add(bottle);
                break;
            case 2:
                HealingPotion healingPotion = new HealingPotion();
                healingPotion.setId((Integer) vars.get(0));
                healingPotion.setName((String) vars.get(1));
                healingPotion.setPrice((Long) vars.get(2));
                healingPotion.setCapacity((Double) vars.get(3));
                healingPotion.setEfficiency((Double) vars.get(4));
                equipments.add(healingPotion);
                break;
            case 3:
                ExpBottle expBottle = new ExpBottle();
                expBottle.setId((Integer) vars.get(0));
                expBottle.setName((String) vars.get(1));
                expBottle.setPrice((Long) vars.get(2));
                expBottle.setCapacity((Double) vars.get(3));
                expBottle.setExpRatio((Double) vars.get(4));
                equipments.add(expBottle);
                break;
            case 4:
                Sword sword = new Sword();
                sword.setId((Integer) vars.get(0));
                sword.setName((String) vars.get(1));
                sword.setPrice((Long) vars.get(2));
                sword.setSharpness((Double) vars.get(3));
                equipments.add(sword);
                break;
            case 5:
                RareSword rareSword = new RareSword();
                rareSword.setId((Integer) vars.get(0));
                rareSword.setName((String) vars.get(1));
                rareSword.setPrice((Long) vars.get(2));
                rareSword.setSharpness((Double) vars.get(3));
                rareSword.setExtraExpBonus((Double) vars.get(4));
                equipments.add(rareSword);
                break;
            case 6:
                EpicSword epicSword = new EpicSword();
                epicSword.setId((Integer) vars.get(0));
                epicSword.setName((String) vars.get(1));
                epicSword.setPrice((Long) vars.get(2));
                epicSword.setSharpness((Double) vars.get(3));
                epicSword.setEvolveRatio((Double) vars.get(4));
                equipments.add(epicSword);
                break;
            default:
                break;
        }
    }

    public void deleteEquipment(int equId) {
        for (Object item : equipments) {
            if (item instanceof Equipment) {
                if (((Equipment) item).getId() == equId) {
                    equipments.remove(item);
                    break;
                }
            }
        }
    }

    public BigInteger sumEquipmentPrice() {
        BigInteger sumPrice = new BigInteger("0");
        for (Object item : equipments) {
            if (item instanceof Equipment) {
                sumPrice = sumPrice.add(BigInteger.valueOf(((Equipment) item).getPrice()));
            }
        }
        return sumPrice;
    }

    public long maxEquipmentPrice() {
        long maxPrice = 0x8000000000000000L;
        for (Object item : equipments) {
            if (item instanceof Equipment) {
                if (((Equipment) item).getPrice() > maxPrice) {
                    maxPrice = ((Equipment) item).getPrice();
                }
            }
        }
        return maxPrice;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getEquipmentNum() {
        return this.equipments.size();
    }

    public void printEquipment(int equId) {
        for (Object item : equipments) {
            if (item instanceof Equipment) {
                if (((Equipment) item).getId() == equId) {
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
                    }
                    break;
                }
            }
        }
    }
}
