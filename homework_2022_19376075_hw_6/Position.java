import com.oocourse.elevator2.PersonRequest;

public class Position {
    private final char building;
    private final int floor;

    public Position(char building, int floor) {
        this.building = building;
        this.floor = floor;
    }

    public boolean contain(PersonRequest request) {
        if (request.getFromBuilding() == building && request.getFromFloor() == floor) {
            return true;
        } else if (request.getToBuilding() == building && request.getToFloor() == floor) {
            return true;
        } else {
            return false;
        }
    }
}
