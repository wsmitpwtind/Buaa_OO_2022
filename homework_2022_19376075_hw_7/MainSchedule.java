import com.oocourse.elevator3.ElevatorRequest;
import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.Request;

import java.util.Objects;
import java.util.Vector;

public class MainSchedule extends Thread {
    private final RequestQueueSchedule waitQueue;
    private final Vector<RequestQueueWait> requestQueueWaits;
    private final Vector<ElevatorProcessPool> elevatorProcessPools;
    private final SplitRequestQueueSchedule queue;

    public MainSchedule(RequestQueueSchedule waitQueueSchedule
            , Vector<RequestQueueWait> requestQueueWaits
            , Vector<ElevatorProcessPool> elevatorProcessPools
            , SplitRequestQueueSchedule queue) {
        this.waitQueue = waitQueueSchedule;
        this.requestQueueWaits = requestQueueWaits;
        this.elevatorProcessPools = elevatorProcessPools;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            //System.out.println("MainSchedule");
            if (waitQueue.isEmpty() && waitQueue.isEnd()) {
                return;
            }
            //System.out.println("MainSchedule1");
            Request request = waitQueue.getOneRequest();
            //System.out.println("MainSchedule2");
            if (request == null) {
                continue;
            }
            //System.out.println("MainSchedule3");
            if (request instanceof PersonRequest) {
                //System.out.println("MainSchedule4");
                wakeUpElevator((PersonRequest) request);
                //System.out.println("MainSchedule5");
            } else if (request instanceof ElevatorRequest) {
                //System.out.println("MainSchedule6");
                addElevator((ElevatorRequest) request);
                //System.out.println("MainSchedule7");
            }
        }
    }

    private synchronized void wakeUpElevator(PersonRequest personRequest) {
        if (personRequest.getFromBuilding() == personRequest.getToBuilding()) {
            requestQueueWaits.get(personRequest.getFromBuilding() - 'A').addRequest(personRequest);
            if (!elevatorProcessPools.get(personRequest.getFromBuilding() - 'A').isAlive()) {
                elevatorProcessPools.get(personRequest.getFromBuilding() - 'A').wakeUp();
            }
        } else {
            requestQueueWaits.get(personRequest.getFromFloor() + 4).addRequest(personRequest);
            if (!elevatorProcessPools.get(personRequest.getFromFloor() + 4).isAlive()) {
                elevatorProcessPools.get(personRequest.getFromFloor() + 4).wakeUp();
            }
        }
        notifyAll();
    }

    private synchronized void addElevator(ElevatorRequest elevatorRequest) {
        if (Objects.equals(elevatorRequest.getType(), "floor")) {
            ElevatorProcessPool tep = elevatorProcessPools.get(elevatorRequest.getFloor() + 4);
            if (tep.getElevatorProcesses().size() > 0) {
                ElevatorProcess preElevatorProcess = tep.getElevatorProcesses().get(0);
                ElevatorProcess elevatorProcess = new ElevatorProcess(
                        elevatorRequest
                        , preElevatorProcess.getWaitingQueue()
                        , queue);
                elevatorProcessPools.get(elevatorRequest.getFloor() + 4).add(elevatorProcess);
            } else {
                ElevatorProcess elevatorProcess = new ElevatorProcess(
                        elevatorRequest
                        , requestQueueWaits.get(elevatorRequest.getFloor() + 4)
                        , queue);
                elevatorProcessPools.get(elevatorRequest.getFloor() + 4).add(elevatorProcess);
            }
        } else if (Objects.equals(elevatorRequest.getType(), "building")) {
            ElevatorProcessPool tep = elevatorProcessPools.get(elevatorRequest.getBuilding() - 'A');
            ElevatorProcess preElevatorProcess = tep.getElevatorProcesses().get(0);
            ElevatorProcess elevatorProcess = new ElevatorProcess(
                    elevatorRequest
                    , preElevatorProcess.getWaitingQueue()
                    , queue);
            elevatorProcessPools.get(elevatorRequest.getBuilding() - 'A').add(elevatorProcess);
        }
        notifyAll();
    }

}


