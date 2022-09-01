import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        int askTimes = scanner.nextInt();
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
                                int botId = scanner.nextInt();
                                String botName = scanner.next();
                                long botPrice = scanner.nextLong();
                                double botCapacity = scanner.nextDouble();
                                item.createBottle(botId, botName, botPrice, botCapacity);
                                break;
                            case 3:
                                item.deleteBottle(scanner.nextInt());
                                break;
                            case 4:
                                System.out.println(item.sumBottlePrice());
                                break;
                            case 5:
                                System.out.println(item.maxBottlePrice());
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