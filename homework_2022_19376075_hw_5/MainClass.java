import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;
import com.oocourse.TimableOutput;
import java.util.Vector;

public class MainClass {
    public static void main(String[] args) throws Exception {
        TimableOutput.initStartTimestamp();
        RequestQueue waitQueue = new RequestQueue();
        Vector<RequestQueue> processingQueues = new Vector<>();
        Vector<ElevatorProcess> elevatorProcesses = new Vector<>();
        for (char type = 'A'; type <= 'E'; type++) {
            RequestQueue parallelQueue = new RequestQueue();
            processingQueues.add(parallelQueue);
            ElevatorProcess process = new ElevatorProcess(parallelQueue, type, type - 'A' + 1);
            elevatorProcesses.add(process);
            //process.start();
        }

        Schedule schedule = new Schedule(waitQueue, processingQueues, elevatorProcesses);
        schedule.start();

        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            PersonRequest personRequest = elevatorInput.nextPersonRequest();
            if (personRequest == null) {
                //System.out.println("end");
                waitQueue.setEnd(true);
                break;
            } else {
                //System.out.println(personRequest);
                Request request = new Request(
                        personRequest.getFromFloor(),
                        personRequest.getToFloor(),
                        personRequest.getFromBuilding(),
                        personRequest.getToBuilding(),
                        personRequest.getPersonId());
                waitQueue.addRequest(request);
            }
        }
        elevatorInput.close();
    }
}
