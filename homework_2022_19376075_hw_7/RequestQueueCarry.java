import com.oocourse.elevator3.PersonRequest;

import java.util.Objects;
import java.util.Vector;

public class RequestQueueCarry {
    private Vector<PersonRequest> requests;
    private final SplitRequestQueueSchedule queue;

    public synchronized int getRequestsNum() {
        int len = requests.size();
        notifyAll();
        return len;
    }

    public RequestQueueCarry(SplitRequestQueueSchedule queue) {
        requests = new Vector<>();
        this.queue = queue;
    }

    public synchronized void addRequest(PersonRequest personRequest) {
        requests.add(personRequest);
        notifyAll();
    }

    public synchronized int getInnerTar(int nowFloor, int nowDirection, String type) {
        if (requests.isEmpty()) {
            notifyAll();
            return -1;
        }
        int maxValue = 0;
        int flag = -1;
        if (Objects.equals(type, "building")) {
            for (int i = 0; i < requests.size(); i++) {
                if ((Math.abs(requests.get(i).getToFloor() - nowFloor) >= maxValue) &&
                        ((requests.get(i).getToFloor() - nowFloor) * nowDirection >= 0)) {
                    maxValue = Math.abs(requests.get(i).getToFloor() - nowFloor);
                    flag = i;
                }
            }
            if (flag == -1) {
                notifyAll();
                return -1;
            }
            notifyAll();
            return requests.get(flag).getToFloor();
        } else {
            if (nowDirection == 0) {
                for (int i = 0; i < requests.size(); i++) {
                    int floor = requests.get(i).getToBuilding() - 'A' + 1;
                    if (floor == (nowFloor + 1) % 5 + 1 || floor == (nowFloor + 2) % 5 + 1) {
                        flag = i;
                        break;
                    }
                    if (floor == (nowFloor) % 5 + 1 || floor == (nowFloor + 3) % 5 + 1) {
                        flag = i;
                    }
                }
            } else {
                for (int i = 0; i < requests.size(); i++) {
                    int floor = requests.get(i).getToBuilding() - 'A' + 1;
                    if (floor == (nowFloor + 2 * nowDirection + 4) % 5 + 1) {
                        flag = i;
                        break;
                    }
                    if (floor == (nowFloor + nowDirection + 4) % 5 + 1) {
                        flag = i;
                    }
                }
            }
            if (flag == -1) {
                notifyAll();
                return -1;
            }
            notifyAll();
            return requests.get(flag).getToBuilding() - 'A' + 1;
        }

    }

    public synchronized boolean isEmpty() {
        boolean ans = requests.isEmpty();
        notifyAll();
        return ans;
    }

    public synchronized void offElevator(String type, ElevatorProcess elevatorProcess) {
        if (Objects.equals(type, "building")) {
            if (requests != null) {
                Vector<PersonRequest> tempRequests = new Vector<>(requests);
                for (PersonRequest request : requests) {
                    if ((request.getToFloor() == elevatorProcess.getNowFloor())
                            && request.getFromBuilding() == request.getToBuilding()) {
                        //System.out.println("S1");
                        elevatorProcess.out(type, request.getPersonId());
                        //System.out.println("S2");
                        elevatorProcess.setCapacity(elevatorProcess.getCapacity() - 1);
                        //System.out.println("S3");
                        tempRequests.remove(request);
                        //System.out.println("S4");
                        queue.addFinish(request.getPersonId());
                        //System.out.println("S5");
                    }
                }
                requests = tempRequests;
            }
        } else {
            if (requests != null) {
                Vector<PersonRequest> tempRequests = new Vector<>(requests);
                for (PersonRequest request : requests) {
                    if (request.getToBuilding() - 'A' + 1 == elevatorProcess.getNowFloor()
                            && request.getFromFloor() == request.getToFloor()) {
                        elevatorProcess.out(type, request.getPersonId());

                        elevatorProcess.setCapacity(elevatorProcess.getCapacity() - 1);

                        tempRequests.remove(request);
                        //System.out.println("r1");
                        queue.addFinish(request.getPersonId());
                        //System.out.println("r2");
                    }
                }
                requests = tempRequests;
            }
        }
        notifyAll();
    }

    public synchronized boolean isOff(ElevatorProcess elevatorProcess, String type) {
        if (Objects.equals(type, "building")) {
            for (PersonRequest request : requests) {
                if (request.getToFloor() == elevatorProcess.getNowFloor()) {
                    notifyAll();
                    return true;
                }
            }
        } else {
            for (PersonRequest request : requests) {
                int nowFloorCode = 1 << (elevatorProcess.getNowFloor() - 1);
                boolean acceptAble = (nowFloorCode & elevatorProcess.getSwitchInfo()) != 0;
                int requestFloor = request.getToBuilding() - 'A' + 1;
                if (requestFloor == elevatorProcess.getNowFloor() && acceptAble) {
                    notifyAll();
                    return true;
                }
            }
        }
        notifyAll();
        return false;
    }
}
