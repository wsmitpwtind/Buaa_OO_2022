public class Request {
    private final int fromFloor;
    private final int toFloor;
    private final char fromBuilding;
    private final char toBuilding;
    private final int personId;

    public Request(int fromFloor, int toFloor, char fromBuilding, char toBuilding, int personId) {
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.personId = personId;
        this.fromBuilding = fromBuilding;
        this.toBuilding = toBuilding;
    }

    public int getFromFloor() {
        return this.fromFloor;
    }

    public int getToFloor() {
        return this.toFloor;
    }

    public char getFromBuilding() {
        return this.fromBuilding;
    }

    public char getToBuilding() {
        return this.toBuilding;
    }

    public int getPersonId() {
        return this.personId;
    }

    public String toString() {
        return String.format("%d-FROM-%c-%d-TO-%c-%d", this.personId
                , this.fromBuilding, this.fromFloor, this.toBuilding, this.toFloor);
    }
}