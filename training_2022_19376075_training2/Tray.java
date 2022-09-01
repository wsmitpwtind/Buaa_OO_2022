public class Tray {
    private int value;
    private boolean full;

    Tray() {
        full = false;
    }

    public synchronized void get() {
        while (!full) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumer get:" + value);
        full = false;
        notifyAll();
    }

    public synchronized void put(int v) {
        while (full) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Producer put:" + v);
        full = true;
        value = v;
        notifyAll();
    }
}