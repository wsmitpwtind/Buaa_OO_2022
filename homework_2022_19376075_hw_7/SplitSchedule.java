import com.oocourse.elevator3.PersonRequest;

import java.util.Vector;

public class SplitSchedule extends Thread {
    private final SplitRequestQueueSchedule splitWaitQueue;
    private final Vector<SplitPoll> splitPolls;
    private final Vector<RequestQueueWait> requestQueueWaits;
    private final Vector<ElevatorProcessPool> elevatorProcessPools;

    public SplitSchedule(SplitRequestQueueSchedule waitQueueSchedule
            , Vector<RequestQueueWait> requestQueueWaits
            , Vector<ElevatorProcessPool> elevatorProcessPools) {
        this.splitWaitQueue = waitQueueSchedule;
        this.splitPolls = splitWaitQueue.getSplitPolls();
        this.requestQueueWaits = requestQueueWaits;
        this.elevatorProcessPools = elevatorProcessPools;


    }

    @Override
    public void run() {
        while (true) {
            //System.out.println("SplitSchedule");
            if (splitPolls.isEmpty() && splitWaitQueue.isEmpty() && splitWaitQueue.isEnd()) {
                return;
            }
            PersonRequest request = splitWaitQueue.getOneRequest();
            if (request == null) {
                continue;
            }
            wakeUpElevator(request);
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

}


