import java.util.Vector;

public class ElevatorProcessPool {

    private final Vector<ElevatorProcess> elevatorProcesses;

    public ElevatorProcessPool() {
        this.elevatorProcesses = new Vector<>();
    }

    public Vector<ElevatorProcess> getElevatorProcesses() {
        return elevatorProcesses;
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

    public void wakeUp() {
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
    }

    public void add(ElevatorProcess elevatorProcess) {
        elevatorProcesses.add(elevatorProcess);
    }
}
