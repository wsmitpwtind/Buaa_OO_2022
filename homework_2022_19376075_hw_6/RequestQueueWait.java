import com.oocourse.elevator2.PersonRequest;

import java.util.Objects;
import java.util.Vector;

public class RequestQueueWait {
    private Vector<PersonRequest> requests;

    public synchronized int getRequestsNum() {
        int len = requests.size();
        notifyAll();
        return len;
    }

    public RequestQueueWait() {
        requests = new Vector<>();
    }

    public synchronized void addRequest(PersonRequest personRequest) {
        requests.add(personRequest);
        notifyAll();
    }

    public synchronized int getOuterTar(int nowFloor, int nowDirection, String type) {
        if (requests.isEmpty()) {
            notifyAll();
            return -1;
        }
        int maxValue = 0;
        int flag = -1;
        if (Objects.equals(type, "building")) {
            for (int i = 0; i < requests.size(); i++) {
                if ((Math.abs(requests.get(i).getFromFloor() - nowFloor) >= maxValue) &&
                        ((requests.get(i).getFromFloor() - nowFloor) * nowDirection >= 0)) {
                    maxValue = Math.abs(requests.get(i).getFromFloor() - nowFloor);
                    flag = i;
                }
            }
            maxValue = 0;
            if (flag == -1) {
                for (int i = 0; i < requests.size(); i++) {
                    if ((Math.abs(requests.get(i).getFromFloor() - nowFloor) >= maxValue) &&
                            ((requests.get(i).getFromFloor() - nowFloor) * nowDirection <= 0)) {
                        maxValue = Math.abs(requests.get(i).getFromFloor() - nowFloor);
                        flag = i;
                    }
                }
            }
            notifyAll();
            return requests.get(flag).getFromFloor();
        } else {
            flag = getFlag(nowDirection, nowFloor);
            if (flag == -1) {
                for (int i = 0; i < requests.size(); i++) {
                    int floor = requests.get(i).getFromBuilding() - 'A' + 1;
                    if (floor == nowFloor) {
                        flag = i;
                    }
                }
                for (int i = 0; i < requests.size(); i++) {
                    int floor = requests.get(i).getFromBuilding() - 'A' + 1;
                    if (floor == (nowFloor - 2 * nowDirection + 4) % 5 + 1) {
                        flag = i;
                        break;
                    }
                    if (floor == (nowFloor - nowDirection + 4) % 5 + 1) {
                        flag = i;
                    }
                }
            }
            notifyAll();
            return requests.get(flag).getFromBuilding() - 'A' + 1;
        }
    }

    public synchronized boolean isEmpty() {
        notifyAll();
        return requests.isEmpty();
    }

    private synchronized int getFlag(int nowDirection, int nowFloor)
    {
        int flag = -1;
        if (nowDirection == 0) {
            for (int i = 0; i < requests.size(); i++) {
                int floor = requests.get(i).getFromBuilding() - 'A' + 1;
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
                int floor = requests.get(i).getFromBuilding() - 'A' + 1;
                if (floor == (nowFloor + 2 * nowDirection + 4) % 5 + 1) {
                    flag = i;
                    break;
                }
                if (floor == (nowFloor + nowDirection + 4) % 5 + 1) {
                    flag = i;
                }
            }
        }
        notifyAll();
        return flag;
    }

    public synchronized void enterElevator(String type, ElevatorProcess elevatorProcess) {
        if (Objects.equals(type, "building")) {
            if (requests != null) {
                Vector<PersonRequest> tempRequests = new Vector<>(requests);
                for (PersonRequest request : requests) {
                    int temp1 = elevatorProcess.getCapacity();
                    int temp2 = elevatorProcess.getElevator().getCapacity();
                    if ((request.getFromFloor() == elevatorProcess.getNowFloor())
                            && (request.getFromBuilding() == request.getToBuilding())
                            && (temp1 != temp2)) {
                        elevatorProcess.getCarryingQueue().addRequest(request);
                        elevatorProcess.in(type, request.getPersonId());
                        elevatorProcess.setCapacity(elevatorProcess.getCapacity() + 1);
                        tempRequests.remove(request);
                    }
                }
                requests = tempRequests;
            }
        } else {
            if (requests != null) {
                Vector<PersonRequest> tempRequests = new Vector<>(requests);
                //System.out.println(tempRequests.size());
                for (PersonRequest request : requests) {
                    int temp1 = elevatorProcess.getCapacity();
                    int temp2 = elevatorProcess.getElevator().getCapacity();
                    if ((request.getFromBuilding() - 'A' + 1 == elevatorProcess.getNowFloor())
                            && (request.getFromFloor() == request.getToFloor())
                            && (temp1 != temp2)) {
                        elevatorProcess.getCarryingQueue().addRequest(request);
                        elevatorProcess.in(type, request.getPersonId());
                        elevatorProcess.setCapacity(elevatorProcess.getCapacity() + 1);
                        tempRequests.remove(request);
                    }
                }
                requests = tempRequests;
            }
        }
        notifyAll();
    }

    public synchronized boolean isIn(ElevatorProcess elevatorProcess, String type) {
        if (elevatorProcess.getCapacity() == elevatorProcess.getElevator().getCapacity()) {
            notifyAll();
            return false;
        }
        if (Objects.equals(type, "building")) {
            for (PersonRequest request : requests) {
                if (request.getFromFloor() == elevatorProcess.getNowFloor()) {
                    notifyAll();
                    return true;
                }
            }
        } else {
            for (PersonRequest request : requests) {
                if (request.getFromBuilding() - 'A' + 1 == elevatorProcess.getNowFloor()) {
                    notifyAll();
                    return true;
                }
            }
        }
        notifyAll();
        return false;
    }
}
