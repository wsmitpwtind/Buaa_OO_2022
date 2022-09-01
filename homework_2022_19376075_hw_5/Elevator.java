public class Elevator {
    private final int moveTime;
    private final int openTime;
    private final int closeTime;
    private final int capacity;

    public Elevator() {
        this.moveTime = 400;
        this.openTime = 200;
        this.closeTime = 200;
        this.capacity = 6;
    }

    public int getMoveTime() {
        return moveTime;
    }

    public int getOpenTime() {
        return openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public int getCapacity() {
        return capacity;
    }
}
