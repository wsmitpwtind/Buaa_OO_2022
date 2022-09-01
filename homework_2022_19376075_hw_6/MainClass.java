import com.oocourse.TimableOutput;
import com.oocourse.elevator2.ElevatorInput;
import com.oocourse.elevator2.Request;

import java.util.Vector;

public class MainClass {
    public static void main(String[] args) throws Exception {
        TimableOutput.initStartTimestamp();
        RequestQueueSchedule waitSchedule = new RequestQueueSchedule();
        Vector<BuildingSchedule> buildSchedules = new Vector<>();
        Vector<ElevatorProcessPool> elevatorProcesses = new Vector<>();
        for (char type = 'A'; type <= 'E'; type++) {
            BuildingSchedule buildingSchedule = new BuildingSchedule();
            RequestQueueWait paraQueue = new RequestQueueWait();
            buildSchedules.add(buildingSchedule);
            ElevatorProcess elevatorProcess = new ElevatorProcess(paraQueue, type, type - 'A' + 1);
            ElevatorProcessPool elevatorProcessPool = new ElevatorProcessPool();
            elevatorProcessPool.getElevatorProcesses().add(elevatorProcess);
            elevatorProcesses.add(elevatorProcessPool);
            buildingSchedule.setElevatorProcessPool(elevatorProcessPool);
        }

        for (char type = 'F'; type <= 'O'; type++) {
            BuildingSchedule buildingSchedule = new BuildingSchedule();
            buildSchedules.add(buildingSchedule);
            ElevatorProcessPool elevatorProcessPool = new ElevatorProcessPool();
            elevatorProcesses.add(elevatorProcessPool);
            buildingSchedule.setElevatorProcessPool(elevatorProcessPool);
        }

        MainSchedule schedule = new MainSchedule(waitSchedule, buildSchedules, elevatorProcesses);
        schedule.start();

        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            Request request = elevatorInput.nextRequest();
            if (request == null) {
                waitSchedule.setEnd(true);
                break;
            } else {
                waitSchedule.addRequest(request);
            }
        }
        elevatorInput.close();
    }
}
