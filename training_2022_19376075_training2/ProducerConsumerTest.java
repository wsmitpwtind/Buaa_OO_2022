public class ProducerConsumerTest {
    public static void main(String[] args) {
        Tray t = new Tray();
        Producer p1 = new Producer(t);
        Consumer c1 = new Consumer(t);
        p1.start();
        c1.start();
    }
}
