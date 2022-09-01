import com.oocourse.elevator2.PersonRequest;

import java.util.Vector;

public class BuildingSchedule extends Thread {
    private ElevatorProcessPool elevatorProcessPool;

    public BuildingSchedule() {
        this.elevatorProcessPool = new ElevatorProcessPool();
    }

    public void setElevatorProcessPool(ElevatorProcessPool elevatorProcessPool) {
        this.elevatorProcessPool = elevatorProcessPool;
    }

    public synchronized void addRequest(PersonRequest personRequest) {
        /*
        if (elevatorProcessPool.getElevatorProcesses().get(0).getType() == "building") {
            if (findSameWay(personRequest) != null) {
                findSameWay(personRequest).getWaitingQueue().addRequest(personRequest);
            } else {
                findLessCapacity(personRequest).getWaitingQueue().addRequest(personRequest);
            }
        } else {
            findLessCapacity(personRequest).getWaitingQueue().addRequest(personRequest);
        }


         */

        findLessCapacity(personRequest).getWaitingQueue().addRequest(personRequest);
        notifyAll();
    }

    private synchronized ElevatorProcess findSameWay(PersonRequest personRequest) {
        Vector<ElevatorProcess> elevatorProcesses = new Vector<>();

        for (ElevatorProcess elevatorProcess : elevatorProcessPool.getElevatorProcesses()) {
            int gap1 = (personRequest.getFromFloor() - elevatorProcess.getNowFloor());
            int gap2 = (personRequest.getFromFloor() - personRequest.getToFloor());
            if (gap1 * elevatorProcess.getNowDirection() >= 0) {
                if (gap2 * elevatorProcess.getNowDirection() >= 0) {
                    elevatorProcesses.add(elevatorProcess);
                }
            }
        }

        if (elevatorProcesses.size() == 0) {
            notifyAll();
            return null;
        } else {
            int minCap = Integer.MAX_VALUE;
            ElevatorProcess newElevatorProcess = null;
            for (ElevatorProcess elevatorProcess : elevatorProcesses) {
                if (elevatorProcess.getCapacity() + elevatorProcess.getWaitQueueLen() < minCap) {
                    if (elevatorProcess.getCapacity() + elevatorProcess.getWaitQueueLen() < 3) {
                        minCap = elevatorProcess.getCapacity() + elevatorProcess.getWaitQueueLen();
                        newElevatorProcess = elevatorProcess;
                    }
                }
            }
            notifyAll();
            return newElevatorProcess;
        }

    }

    private synchronized ElevatorProcess findLessCapacity(PersonRequest personRequest) {
        int minCap = Integer.MAX_VALUE;
        int minD = Integer.MAX_VALUE;
        ElevatorProcess newElevatorProcess = null;
        Vector<ElevatorProcess> elevatorProcesses = new Vector<>();
        for (ElevatorProcess elevatorProcess : elevatorProcessPool.getElevatorProcesses()) {
            if (elevatorProcess.getCapacity() + elevatorProcess.getWaitQueueLen() < minCap) {
                minCap = elevatorProcess.getCapacity() + elevatorProcess.getWaitQueueLen();
                newElevatorProcess = elevatorProcess;
            }
            if (elevatorProcess.getCapacity() + elevatorProcess.getWaitQueueLen() == 0) {
                elevatorProcesses.add(elevatorProcess);
            }
        }

        if (elevatorProcesses.size() != 0) {
            for (ElevatorProcess elevatorProcess : elevatorProcesses) {
                if (Math.abs(elevatorProcess.getNowFloor() - personRequest.getFromFloor()) < minD) {
                    minD = elevatorProcess.getCapacity();
                    newElevatorProcess = elevatorProcess;
                }
            }
        }
        notifyAll();
        return newElevatorProcess;
    }
}
