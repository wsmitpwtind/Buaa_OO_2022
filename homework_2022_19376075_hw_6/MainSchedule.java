import com.oocourse.elevator2.ElevatorRequest;
import com.oocourse.elevator2.PersonRequest;
import com.oocourse.elevator2.Request;

import java.util.Objects;
import java.util.Vector;

public class MainSchedule extends Thread {
    private final RequestQueueSchedule waitQueue;
    private final Vector<BuildingSchedule> buildingSchedules;
    private final Vector<ElevatorProcessPool> elevatorProcessPools;

    public MainSchedule(RequestQueueSchedule waitQueueSchedule
            , Vector<BuildingSchedule> buildingSchedules
            , Vector<ElevatorProcessPool> elevatorProcessPools) {
        this.waitQueue = waitQueueSchedule;
        this.buildingSchedules = buildingSchedules;
        this.elevatorProcessPools = elevatorProcessPools;
    }

    @Override
    public void run() {
        while (true) {
            if (waitQueue.isEmpty() && waitQueue.isEnd()) {
                return;
            }
            Request request = waitQueue.getOneRequest();
            if (request == null) {
                continue;
            }
            if (request instanceof PersonRequest) {
                wakeUpElevator((PersonRequest) request);
            } else if (request instanceof ElevatorRequest) {
                addElevator((ElevatorRequest) request);
            }
        }
    }

    private synchronized void wakeUpElevator(PersonRequest personRequest) {
        if (personRequest.getFromBuilding() == personRequest.getToBuilding()) {
            buildingSchedules.get(personRequest.getFromBuilding() - 'A').addRequest(personRequest);
            if (!elevatorProcessPools.get(personRequest.getFromBuilding() - 'A').isAlive()) {
                elevatorProcessPools.get(personRequest.getFromBuilding() - 'A').wakeUp();
            }
        } else {
            buildingSchedules.get(personRequest.getFromFloor() + 4).addRequest(personRequest);
            if (!elevatorProcessPools.get(personRequest.getFromFloor() + 4).isAlive()) {
                elevatorProcessPools.get(personRequest.getFromFloor() + 4).wakeUp();
            }
        }
        notifyAll();
    }

    private synchronized void addElevator(ElevatorRequest elevatorRequest) {
        RequestQueueWait requestQueueWait = new RequestQueueWait();
        ElevatorProcess elevatorProcess = new ElevatorProcess(
                elevatorRequest, requestQueueWait);
        if (Objects.equals(elevatorRequest.getType(), "floor")) {
            elevatorProcessPools.get(elevatorRequest.getFloor() + 4).add(elevatorProcess);
        } else if (Objects.equals(elevatorRequest.getType(), "building")) {
            elevatorProcessPools.get(elevatorRequest.getBuilding() - 'A').add(elevatorProcess);
        }
        notifyAll();
    }

}


