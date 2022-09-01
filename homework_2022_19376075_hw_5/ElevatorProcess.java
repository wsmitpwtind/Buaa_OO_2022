import com.oocourse.TimableOutput;

import java.util.Objects;

public class ElevatorProcess extends Thread {
    private final RequestQueue processingQueue;
    private final char building;
    private final Elevator elevator;
    private final AcceptablePosition acceptablePosition;

    private String status;

    private final RequestQueue passengers;

    private int capacity;

    private int nowFloor;
    private int target;

    private final int identifier;

    public Elevator getElevator() {
        return elevator;
    }

    public RequestQueue getPassengers() {
        return passengers;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNowFloor() {
        return nowFloor;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ElevatorProcess(RequestQueue processingQueue, char building, int id) {
        this.processingQueue = processingQueue;
        this.building = building;
        elevator = new Elevator();
        acceptablePosition = new AcceptablePosition(building);
        status = "Waiting";
        passengers = new RequestQueue();
        capacity = 0;
        nowFloor = 1;
        target = 1;
        identifier = id;
    }

    public ElevatorProcess(ElevatorProcess elevatorProcess) {
        processingQueue = elevatorProcess.processingQueue;
        building = elevatorProcess.building;
        elevator = elevatorProcess.elevator;
        acceptablePosition = elevatorProcess.acceptablePosition;
        status = "Waiting";
        passengers = new RequestQueue();
        capacity = 0;
        nowFloor = elevatorProcess.getNowFloor();
        target = elevatorProcess.getNowFloor();
        identifier = elevatorProcess.identifier;
    }

    @Override
    public void run() {
        while (!processingQueue.isEmpty() || !passengers.isEmpty() || !status.equals("Waiting")) {
            //System.out.println(building + " " + nowFloor + " " + this);
            gettingTarget();
            dealRequest();
        }
        //System.out.println(building + " ElevatorProcess End");
    }

    private void gettingTarget() {
        Request outRequest = processingQueue.getOneRequestOutside(nowFloor);
        Request innerRequest = passengers.getOneRequestInner(nowFloor);
        if (capacity == elevator.getCapacity()) {
            target = innerRequest.getToFloor();
        } else if (outRequest != null && innerRequest == null) {
            target = outRequest.getFromFloor();
        } else if (outRequest == null && innerRequest != null) {
            target = innerRequest.getToFloor();
        } else if (outRequest != null) {
            if (Math.abs(outRequest.getFromFloor() - nowFloor) >
                    Math.abs(innerRequest.getToFloor() - nowFloor)) {
                target = innerRequest.getToFloor();
                //target = outRequest.getFromFloor();
            } else {
                //target = innerRequest.getToFloor();
                target = outRequest.getFromFloor();
            }
        }
    }

    private void dealRequest() {
        switch (status) {
            case "Arrived":
                arrive();
                if (target == nowFloor) {
                    status = "Opening";
                } else {
                    status = "Waiting";
                }
                break;
            case "Waiting":
                if (target < nowFloor) {
                    status = "Down";
                } else if (target > nowFloor) {
                    status = "Up";
                } else {
                    status = "Opening";
                }
                break;
            case "Opening":
                open();
                openDoor();
                try {
                    Thread.sleep(elevator.getOpenTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                openDoor();
                status = "Closing";
                break;
            case "Closing":
                openDoor();
                try {
                    Thread.sleep(elevator.getCloseTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                openDoor();
                close();
                status = "Waiting";
                break;
            case "Up":
            case "Down":
                try {
                    Thread.sleep(elevator.getMoveTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nowFloor = (Objects.equals(status, "Up")) ? nowFloor + 1 : nowFloor - 1;
                status = "Arrived";
                break;
            default:
                break;
        }
    }

    private void openDoor() {
        passengers.offElevator(this);
        processingQueue.enterElevator(this);
    }

    private void open() {
        println(String.format("OPEN-%c-%d-%d"
                , building, nowFloor, identifier));
    }

    private void close() {
        println(String.format("CLOSE-%c-%d-%d"
                , building, nowFloor, identifier));
    }

    private void arrive() {
        println(String.format("ARRIVE-%c-%d-%d"
                , building, nowFloor, identifier));
    }

    public void in(int id) {
        println(String.format("IN-%d-%c-%d-%d"
                , id, building, nowFloor, identifier));
    }

    public void out(int id) {
        println(String.format("OUT-%d-%c-%d-%d"
                , id, building, nowFloor, identifier));
    }

    public static synchronized void println(String str) {
        TimableOutput.println(str);
    }
}

