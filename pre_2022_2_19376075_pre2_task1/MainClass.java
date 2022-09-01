import java.util.Scanner;

@SuppressWarnings("checkstyle:EmptyLineSeparator")
public class MainClass {

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        Bottle bottle = new Bottle();  //new Bottle() 即构造函数
        bottle.setId(scanner.nextInt());
        bottle.setName(scanner.next());
        bottle.setPrice(scanner.nextLong());
        bottle.setCapacity(scanner.nextDouble());
        bottle.setFilled(true);
        int askTimes = scanner.nextInt();
        for (int i = 1;i <= askTimes;i++)
        {
            int status = scanner.nextInt();
            switch (status)
            {
                case 1:
                    System.out.println(bottle.getName());
                    break;
                case 2:
                    System.out.println(bottle.getPrice());
                    break;
                case 3:
                    System.out.println(bottle.getCapacity());
                    break;
                case 4:
                    System.out.println(bottle.getFilled());
                    break;
                case 5:
                    long price = scanner.nextLong();
                    bottle.setPrice(price);
                    break;
                case 6:
                    boolean filled = scanner.nextBoolean();
                    bottle.setFilled(filled);
                    break;
                case 7:
                    System.out.println(bottle.getSituation());
                    break;
                default:
                    break;
            }
        }
    }
}
