import java.util.Vector;

public class RequestQueue {
    private Vector<Request> requests;
    private boolean isEnd;

    public RequestQueue() {
        requests = new Vector<>();
        this.isEnd = false;
    }

    public synchronized void addRequest(Request request) {
        requests.add(request);
        notifyAll();
    }

    public synchronized Request getOneRequestOutside(int nowFloor) {
        if (requests.isEmpty()) {
            return null;
        }
        int minDistance = Integer.MAX_VALUE;
        int flag = 0;
        for (int i = 0; i < requests.size(); i++) {
            if (Math.abs(requests.get(i).getFromFloor() - nowFloor) < minDistance) {
                minDistance = Math.abs(requests.get(i).getFromFloor() - nowFloor);
                flag = i;
            }
        }
        Request request = requests.get(flag);
        //注：此处 “没有” 删除
        notifyAll();
        return request;
    }

    public synchronized Request getOneRequestInner(int nowFloor) {
        if (requests.isEmpty()) {
            return null;
        }
        int minDistance = Integer.MAX_VALUE;
        int flag = 0;
        for (int i = 0; i < requests.size(); i++) {
            if (Math.abs(requests.get(i).getToFloor() - nowFloor) < minDistance) {
                minDistance = Math.abs(requests.get(i).getToFloor() - nowFloor);
                flag = i;
            }
        }
        Request request = requests.get(flag);
        //注：此处 “没有” 删除
        notifyAll();
        return request;
    }

    public synchronized Request getOneRequest() {
        if (requests.isEmpty() && !isEnd) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (requests.isEmpty()) {
            return null;
        }
        Request request = requests.get(0);
        //注：此处进行了删除
        requests.remove(0);
        notifyAll();
        return request;
    }

    public synchronized void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
        notifyAll();
    }

    public synchronized boolean isEnd() {
        notifyAll();
        return isEnd;
    }

    public synchronized boolean isEmpty() {
        notifyAll();
        return requests.isEmpty();
    }

    public synchronized void offElevator(ElevatorProcess elevatorProcess) {
        if (requests != null) {
            Vector<Request> tempRequests = new Vector<>(requests);
            for (Request request : requests) {
                if (request.getToFloor() == elevatorProcess.getNowFloor()) {
                    elevatorProcess.out(request.getPersonId());
                    elevatorProcess.setCapacity(elevatorProcess.getCapacity() - 1);
                    tempRequests.remove(request);
                }
            }
            requests = tempRequests;
        }
        notifyAll();
    }

    public synchronized void enterElevator(ElevatorProcess elevatorProcess) {
        Request outRequest = this.getOneRequestOutside(elevatorProcess.getNowFloor());
        while (true) {
            if (outRequest == null) {
                break;
            }
            if (outRequest.getFromFloor() != elevatorProcess.getNowFloor()) {
                break;
            }
            if (elevatorProcess.getCapacity() == elevatorProcess.getElevator().getCapacity()) {
                break;
            }
            elevatorProcess.getPassengers().addRequest(outRequest);
            elevatorProcess.setCapacity(elevatorProcess.getCapacity() + 1);
            elevatorProcess.in(outRequest.getPersonId());
            requests.remove(outRequest);
            outRequest = this.getOneRequestOutside(elevatorProcess.getNowFloor());
        }
        notifyAll();
    }
}
