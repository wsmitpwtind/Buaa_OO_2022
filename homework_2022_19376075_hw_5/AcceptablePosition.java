import java.util.Vector;

public class AcceptablePosition {
    private final Vector<Position> acceptablePositions = new Vector<>();

    public AcceptablePosition(char building) {
        for (int i = 1; i <= 10; i++) {
            acceptablePositions.add(new Position(building, i));
        }
    }

    public boolean requestAcceptable(Request request)
    {
        int temp = 0;
        for (Position position : acceptablePositions)
        {
            if (position.contain(request))
            {
                temp++;
            }
        }
        return (temp == 2);
    }
}
