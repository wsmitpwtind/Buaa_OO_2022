import com.oocourse.elevator2.ElevatorRequest;
import com.oocourse.elevator2.Request;

import java.util.Vector;

public class RequestQueueSchedule {
    private final Vector<Request> requests;
    private boolean isEnd;

    public RequestQueueSchedule() {
        requests = new Vector<>();
        this.isEnd = false;
    }

    public synchronized void addRequest(Request request) {
        requests.add(request);
        notifyAll();
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
            notifyAll();
            return null;
        }
        for (Request request : requests) {
            if (request instanceof ElevatorRequest)
            {
                requests.remove(request);
                notifyAll();
                return request;
            }
        }
        Request request = requests.get(0);
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

}
