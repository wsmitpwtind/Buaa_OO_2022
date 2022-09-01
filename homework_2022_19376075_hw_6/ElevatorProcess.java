import com.oocourse.TimableOutput;
import com.oocourse.elevator2.ElevatorRequest;

import java.util.Objects;

public class ElevatorProcess extends Thread {
    private final RequestQueueWait waitingQueue;
    private final RequestQueueCarry carryingQueue;

    private final char building;
    private int capacity;
    private final Elevator elevator;

    private final String type;
    //private final AcceptablePosition acceptablePosition;

    private String status;

    private int nowFloor;

    private int nowDirection;
    private int target;

    private final int identifier;

    public Elevator getElevator() {
        return elevator;
    }

    public RequestQueueCarry getCarryingQueue() {
        return carryingQueue;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNowFloor() {
        return nowFloor;
    }

    public int getNowDirection() {
        return nowDirection;
    }

    public int getWaitQueueLen() {
        return waitingQueue.getRequestsNum();
    }

    public int getCarryingQueueLen() {
        return carryingQueue.getRequestsNum();
    }

    public String getType() {
        return type;
    }

    public RequestQueueWait getWaitingQueue() {
        return waitingQueue;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ElevatorProcess(RequestQueueWait processingQueue, char building, int id) {
        this.waitingQueue = processingQueue;
        this.building = building;
        //acceptablePosition = new AcceptablePosition(building);
        status = "Waiting";
        carryingQueue = new RequestQueueCarry();
        capacity = 0;
        nowFloor = 1;
        nowDirection = 0;
        target = 1;
        identifier = id;
        type = "building";
        elevator = new Elevator(type);
    }

    public ElevatorProcess(ElevatorProcess elevatorProcess) {
        waitingQueue = elevatorProcess.waitingQueue;
        building = elevatorProcess.building;
        elevator = elevatorProcess.elevator;
        //acceptablePosition = elevatorProcess.acceptablePosition;
        status = "Waiting";
        carryingQueue = new RequestQueueCarry();
        capacity = 0;
        nowFloor = elevatorProcess.getNowFloor();
        nowDirection = 0;
        target = elevatorProcess.getNowFloor();
        identifier = elevatorProcess.identifier;
        type = elevatorProcess.type;
    }

    public ElevatorProcess(ElevatorRequest elevatorRequest, RequestQueueWait processingQueue) {
        this.waitingQueue = processingQueue;
        elevator = new Elevator(elevatorRequest.getType());
        //acceptablePosition = new AcceptablePosition(building);
        status = "Waiting";
        carryingQueue = new RequestQueueCarry();
        capacity = 0;
        nowFloor = 1;
        nowDirection = 0;
        target = 1;
        identifier = elevatorRequest.getElevatorId();
        type = elevatorRequest.getType();
        if (Objects.equals(type, "floor")) {
            this.building = (char) (elevatorRequest.getFloor() + 'A' + 4);
        } else {
            this.building = elevatorRequest.getBuilding();
        }
    }

    @Override
    public void run() {
        while (!waitingQueue.isEmpty() || !carryingQueue.isEmpty() || !status.equals("Waiting")) {
            gettingTarget();
            dealRequest();
        }
    }

    private void gettingTarget() {
        boolean fromInner = false;
        if (!carryingQueue.isEmpty()) {
            if (carryingQueue.getInnerTar(nowFloor, nowDirection, type) != -1) {
                target = carryingQueue.getInnerTar(nowFloor, nowDirection, type);
                fromInner = true;
            }
        }
        if (!fromInner) {
            if (waitingQueue.getOuterTar(nowFloor, nowDirection, type) != -1) {
                target = waitingQueue.getOuterTar(nowFloor, nowDirection, type);
            }
        }
        if (Objects.equals(type, "building")) {
            if (target != nowFloor) {
                nowDirection = Integer.compare(target, nowFloor);
            }
        } else {
            //System.out.println(fromInner);
            if (target != nowFloor) {
                //System.out.println("nowFloor = " + nowFloor + " target = " + target);
                int num = nowFloor - target;
                nowDirection = Integer.compare((num + 5) % 5, (-num + 5) % 5);
                //System.out.println("nowDirection = " + nowDirection);
            }
        }
        //System.out.println(nowDirection);
    }

    private boolean checkIO() {
        return waitingQueue.isIn(this, this.type) || carryingQueue.isOff(this, this.type);
    }

    private void dealRequest() {
        switch (status) {
            case "Arrived":
                arrive(type);
                if (target == nowFloor) {
                    status = "Opening";
                } else {
                    status = "Waiting";
                }
                break;
            case "Waiting":
                if (checkIO()) {
                    status = "Opening";
                } else if (nowDirection == 1) {
                    status = "Up";
                } else if (nowDirection == -1) {
                    status = "Down";
                }
                break;
            case "Opening":
                open(type);
                openDoor();
                try {
                    Thread.sleep(elevator.getOpenTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                status = "Closing";
                openDoor();
                break;
            case "Closing":
                openDoor();
                try {
                    Thread.sleep(elevator.getCloseTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                openDoor();
                close(type);
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
                if (Objects.equals(type, "floor")) {
                    nowFloor = change(nowFloor);
                } else {
                    nowDirection = (nowFloor == 10 || nowFloor == 1) ? 0 : nowDirection;
                }
                status = "Arrived";
                break;
            default:
                break;
        }
    }

    private int change(int nowFloor) {
        if (nowFloor == 0) {
            return 5;
        } else if (nowFloor == 6) {
            return 1;
        }
        return nowFloor;
    }

    private void openDoor() {
        carryingQueue.offElevator(type, this);
        waitingQueue.enterElevator(type, this);
    }

    private void open(String type) {
        if (Objects.equals(type, "building")) {
            println(String.format("OPEN-%c-%d-%d"
                    , building, nowFloor, identifier));
        } else {
            println(String.format("OPEN-%c-%d-%d"
                    , nowFloor + 'A' - 1, building - 'A' - 4, identifier));
        }
    }

    private void close(String type) {
        if (Objects.equals(type, "building")) {
            println(String.format("CLOSE-%c-%d-%d"
                    , building, nowFloor, identifier));
        } else {
            println(String.format("CLOSE-%c-%d-%d"
                    , nowFloor + 'A' - 1, building - 'A' - 4, identifier));
        }
    }

    private void arrive(String type) {
        if (Objects.equals(type, "building")) {
            println(String.format("ARRIVE-%c-%d-%d"
                    , building, nowFloor, identifier));
        } else {
            println(String.format("ARRIVE-%c-%d-%d"
                    , nowFloor + 'A' - 1, building - 'A' - 4, identifier));
        }
    }

    public void in(String type, int id) {
        if (Objects.equals(type, "building")) {
            println(String.format("IN-%d-%c-%d-%d"
                    , id, building, nowFloor, identifier));
        } else {
            println(String.format("IN-%d-%c-%d-%d"
                    , id, nowFloor + 'A' - 1, building - 'A' - 4, identifier));
        }
    }

    public void out(String type, int id) {
        if (Objects.equals(type, "building")) {
            println(String.format("OUT-%d-%c-%d-%d"
                    , id, building, nowFloor, identifier));
        } else {
            println(String.format("OUT-%d-%c-%d-%d"
                    , id, nowFloor + 'A' - 1, building - 'A' - 4, identifier));
        }
    }

    public static synchronized void println(String str) {
        TimableOutput.println(str);
    }
}

