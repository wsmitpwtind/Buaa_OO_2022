import java.util.Vector;

public class Schedule extends Thread {
    private final RequestQueue waitQueue;
    private final Vector<RequestQueue> processingQueues;
    private final Vector<ElevatorProcess> elevatorProcesses;

    public Schedule(RequestQueue waitQueue
            , Vector<RequestQueue> processingQueues, Vector<ElevatorProcess> elevatorProcesses) {
        this.waitQueue = waitQueue;
        this.processingQueues = processingQueues;
        this.elevatorProcesses = elevatorProcesses;
    }

    @Override
    public void run() {
        while (true) {
            if (waitQueue.isEmpty() && waitQueue.isEnd()) {
                for (int i = 0; i < processingQueues.size(); i++) {
                    processingQueues.get(i).setEnd(true);
                }
                //System.out.println("Schedule End");
                return;
            }
            Request request = waitQueue.getOneRequest();
            if (request == null) {
                continue;
            }
            if (request.getFromBuilding() == 'A') {
                processingQueues.get(0).addRequest(request);
                if (!elevatorProcesses.get(0).isAlive()) {
                    ElevatorProcess tempElevatorProcesses =
                            new ElevatorProcess(elevatorProcesses.get(0));
                    elevatorProcesses.remove(0);
                    elevatorProcesses.add(0,tempElevatorProcesses);
                    tempElevatorProcesses.start();
                }
            } else if (request.getFromBuilding() == 'B') {
                processingQueues.get(1).addRequest(request);
                if (!elevatorProcesses.get(1).isAlive()) {
                    ElevatorProcess tempElevatorProcesses =
                            new ElevatorProcess(elevatorProcesses.get(1));
                    elevatorProcesses.remove(1);
                    elevatorProcesses.add(1,tempElevatorProcesses);
                    tempElevatorProcesses.start();
                }
            } else if (request.getFromBuilding() == 'C') {
                processingQueues.get(2).addRequest(request);
                if (!elevatorProcesses.get(2).isAlive()) {
                    ElevatorProcess tempElevatorProcesses =
                            new ElevatorProcess(elevatorProcesses.get(2));
                    elevatorProcesses.remove(2);
                    elevatorProcesses.add(2,tempElevatorProcesses);
                    tempElevatorProcesses.start();
                }
            } else if (request.getFromBuilding() == 'D') {
                processingQueues.get(3).addRequest(request);
                if (!elevatorProcesses.get(3).isAlive()) {
                    ElevatorProcess tempElevatorProcesses =
                            new ElevatorProcess(elevatorProcesses.get(3));
                    elevatorProcesses.remove(3);
                    elevatorProcesses.add(3,tempElevatorProcesses);
                    tempElevatorProcesses.start();
                }
            } else if (request.getFromBuilding() == 'E') {
                processingQueues.get(4).addRequest(request);
                if (!elevatorProcesses.get(4).isAlive()) {
                    ElevatorProcess tempElevatorProcesses =
                            new ElevatorProcess(elevatorProcesses.get(4));
                    elevatorProcesses.remove(4);
                    elevatorProcesses.add(4,tempElevatorProcesses);
                    tempElevatorProcesses.start();
                }
            }
        }
    }

    private void bootElevator(char building, Request request)
    {
        processingQueues.get(building - 'A').addRequest(request);
        if (!elevatorProcesses.get(building - 'A').isAlive()) {
            elevatorProcesses.get(building - 'A').start();
        }
    }
}


