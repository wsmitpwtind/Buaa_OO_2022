import com.oocourse.TimableOutput;
import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.ElevatorRequest;
import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.Request;

import java.util.Vector;

public class MainClass {
    public static void main(String[] args) throws Exception {
        TimableOutput.initStartTimestamp();
        Vector<RequestQueueWait> requestQueueWaits = new Vector<>();
        Vector<ElevatorProcessPool> elevatorProcesses = new Vector<>();
        SplitRequestQueueSchedule queue = new SplitRequestQueueSchedule(elevatorProcesses);

        for (char type = 'A'; type <= 'E'; type++) {
            RequestQueueWait paraQueue = new RequestQueueWait();
            requestQueueWaits.add(paraQueue);
            ElevatorProcess elevatorProcess = new ElevatorProcess(
                    paraQueue, type, type - 'A' + 1, queue);
            ElevatorProcessPool elevatorProcessPool = new ElevatorProcessPool();
            elevatorProcessPool.getElevatorProcesses().add(elevatorProcess);
            elevatorProcesses.add(elevatorProcessPool);
        }

        for (char type = 'F'; type <= 'O'; type++) {
            RequestQueueWait paraQueue = new RequestQueueWait();
            requestQueueWaits.add(paraQueue);
            ElevatorProcessPool elevatorProcessPool = new ElevatorProcessPool();
            elevatorProcesses.add(elevatorProcessPool);
        }

        RequestQueueWait paraQueue = requestQueueWaits.get(5);
        ElevatorProcess elevatorProcess = new ElevatorProcess(paraQueue, 'F', 6, queue);
        elevatorProcesses.get(5).getElevatorProcesses().add(elevatorProcess);

        RequestQueueSchedule schedule = new RequestQueueSchedule();
        MainSchedule theSchedule = new MainSchedule(schedule, requestQueueWaits
                , elevatorProcesses, queue);
        theSchedule.start();

        SplitSchedule split = new SplitSchedule(queue, requestQueueWaits, elevatorProcesses);
        split.start();

        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            //System.out.println("MainClass");
            Request request = elevatorInput.nextRequest();
            if (request == null) {
                schedule.setEnd(true);
                queue.setEnd(true);
                break;
            } else {
                if (request instanceof ElevatorRequest) {
                    //System.out.println("MainClass1");
                    schedule.addRequest(request);
                    //System.out.println("MainClass2");
                } else if (request instanceof PersonRequest) {
                    //System.out.println("MainClass3");
                    queue.addRequest((PersonRequest) request);
                    //System.out.println("MainClass4");
                }
            }
        }
        elevatorInput.close();
    }
}
