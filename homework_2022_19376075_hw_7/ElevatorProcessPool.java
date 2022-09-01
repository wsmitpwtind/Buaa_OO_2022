import java.util.Vector;

public class ElevatorProcessPool {

    private final Vector<ElevatorProcess> elevatorProcesses;

    public ElevatorProcessPool() {
        this.elevatorProcesses = new Vector<>();
    }

    public Vector<ElevatorProcess> getElevatorProcesses() {
        return elevatorProcesses;
    }

    public synchronized boolean isEmpty(char nowBuilding)
    {
        int code = 1 << (nowBuilding - 'A');
        boolean answer = true;
        if (!elevatorProcesses.isEmpty()) {
            for (ElevatorProcess elevatorProcess : elevatorProcesses) {
                if ((elevatorProcess.getSwitchInfo() & code) != 0) {
                    answer = false;
                    break;
                }
            }
        }
        notifyAll();
        return answer;
    }

    public boolean isAlive() {
        boolean allAlive = true;
        for (ElevatorProcess elevatorProcess : elevatorProcesses) {
            if (!elevatorProcess.isAlive()) {
                allAlive = false;
                break;
            }
        }
        return allAlive;
    }

    public synchronized void wakeUp() {
        int size = elevatorProcesses.size();
        for (int i = 0; i < size; i++) {
            if (!elevatorProcesses.get(i).isAlive()) {
                ElevatorProcess tempElevatorProcesses =
                        new ElevatorProcess(elevatorProcesses.get(i));
                elevatorProcesses.remove(i);
                elevatorProcesses.add(i, tempElevatorProcesses);
                tempElevatorProcesses.start();
            }
        }
        notifyAll();
    }

    public void add(ElevatorProcess elevatorProcess) {
        elevatorProcesses.add(elevatorProcess);
    }

    public synchronized double getCost() {
        double totalSpeed = 0;
        double totalCapacity = 0;
        for (ElevatorProcess elevatorProcess : elevatorProcesses) {
            totalSpeed += elevatorProcess.getElevator().getMoveTime();
            totalCapacity += elevatorProcess.getElevator().getCapacity();
        }
        double aveSpeed = totalSpeed / elevatorProcesses.size();
        double aveCapacity = totalCapacity / elevatorProcesses.size();
        int totalWait = elevatorProcesses.get(0).getWaitQueueLen();
        int elevatorNum = elevatorProcesses.size();
        notifyAll();
        return aveSpeed * (1 + totalWait / totalCapacity);
    }

    public synchronized double getCost(char fb, char tb) {
        double totalSpeed = 0;
        double totalCapacity = 0;
        int code;
        for (ElevatorProcess elevatorProcess : elevatorProcesses) {
            code = (1 << (fb - 'A')) + (1 << (tb - 'A'));
            if ((code & elevatorProcess.getSwitchInfo()) != 0) {
                totalSpeed += elevatorProcess.getElevator().getMoveTime();
                totalCapacity += elevatorProcess.getElevator().getCapacity();
            }
        }
        double aveSpeed = totalSpeed / elevatorProcesses.size();
        double aveCapacity = totalCapacity / elevatorProcesses.size();
        int totalWait = elevatorProcesses.get(0).getWaitQueueLen();
        int elevatorNum = elevatorProcesses.size();
        notifyAll();
        return aveSpeed * (1 + totalWait / totalCapacity);
    }
}
