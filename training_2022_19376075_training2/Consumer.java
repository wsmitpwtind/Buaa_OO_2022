public class Consumer extends Thread {
    private Tray tray;

    public Consumer(Tray t) {
        tray = t;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            tray.get();
        }
    }
}