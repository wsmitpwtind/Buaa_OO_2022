import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        int askTimes = scanner.nextInt();
        int equType;
        for (int i = 1; i <= askTimes; i++) {
            int status = scanner.nextInt();
            if (status == 1) {
                Adventurer adventurer = new Adventurer();
                adventurer.setAdventurer(scanner.nextInt(), scanner.next());
                adventurers.add(adventurer);
            } else {
                int adventurerId = scanner.nextInt();
                for (Adventurer item : adventurers) {
                    if (item.getId() == adventurerId) {
                        switch (status) {
                            case 2:
                                equType = scanner.nextInt();
                                List<java.io.Serializable> vars = new ArrayList<>();
                                vars.add(scanner.nextInt());
                                vars.add(scanner.next());
                                vars.add(scanner.nextLong());
                                vars.add(scanner.nextDouble());
                                if (equType != 1 && equType != 4) {
                                    vars.add(scanner.nextDouble());
                                }
                                item.createEquipment(equType, vars);
                                break;
                            case 3:
                                item.deleteEquipment(scanner.nextInt());
                                break;
                            case 4:
                                System.out.println(item.sumEquipmentPrice());
                                break;
                            case 5:
                                System.out.println(item.maxEquipmentPrice());
                                break;
                            case 6:
                                System.out.println(item.getEquipmentNum());
                                break;
                            case 7:
                                int equId = scanner.nextInt();
                                item.printEquipment(equId);
                                break;
                            case 8:
                                item.useEquipment();
                                break;
                            case 9:
                                item.printAdventurer();
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                }
            }
        }
    }

}