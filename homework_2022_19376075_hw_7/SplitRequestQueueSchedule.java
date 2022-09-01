import com.oocourse.elevator3.PersonRequest;

import java.util.Vector;

public class SplitRequestQueueSchedule {

    private final Vector<SplitPoll> splitPolls;
    private final Vector<ElevatorProcessPool> elevatorProcesses;
    private boolean isEnd;
    private Vector<Integer> finishID;

    public SplitRequestQueueSchedule(Vector<ElevatorProcessPool> elevatorProcesses) {
        splitPolls = new Vector<>();
        this.elevatorProcesses = elevatorProcesses;
        this.isEnd = false;
        this.finishID = new Vector<>();
    }

    public Vector<SplitPoll> getSplitPolls() {
        return splitPolls;
    }

    public synchronized void addRequest(PersonRequest personRequest) {
        SplitPoll splitPoll = new SplitPoll(personRequest, elevatorProcesses);
        splitPolls.add(splitPoll);
        addFinish(personRequest.getPersonId());
        notifyAll();
    }

    public synchronized void addFinish(int finish) {
        finishID.add(finish);
        notifyAll();
    }

    public synchronized PersonRequest getOneRequest() {
        //System.out.println("Waiting in SQS");
        //System.out.println(finishID.size());
        if (finishID.size() != 0) {
            //System.out.println("ID = " + finishID.get(0));
        }
        //System.out.println(splitPolls.size());
        if (splitPolls.size() != 0) {
            //System.out.println("splitPolls = " + splitPolls.get(0).getPersonID());
        }
        if ((splitPolls.isEmpty() || finishID.
                isEmpty()) && !(splitPolls.isEmpty() && finishID.isEmpty() && isEnd)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("Waiting in SQS OUT");

        if ((splitPolls.isEmpty() || finishID.isEmpty())) {
            if (splitPolls.isEmpty()) {
                int t = finishID.size();
                for (int i = 0; i < t; i++) {
                    finishID.remove(0);
                }
            }
            //System.out.println("null1");
            notifyAll();
            return null;
        }
        SplitPoll temp = null;
        Vector<Integer> copy = new Vector<>(finishID);
        boolean isIN = false;
        int t = 0;
        for (int i = 0; i < finishID.size(); i++) {
            for (SplitPoll splitPoll : splitPolls) {
                if (splitPoll.getPersonID() == finishID.get(i)) {
                    temp = splitPoll;
                    copy.remove(i - t);
                    t++;
                    isIN = true;
                    break;
                }
            }
            if (isIN) {
                break;
            } else {
                copy.remove(i - t);
                t++;
            }
        }
        finishID = new Vector<>(copy);


        if (temp == null) {
            notifyAll();
            return null;
        } else {
            PersonRequest personRequest = temp.finishStage();
            if (temp.getSize() == 0) {
                splitPolls.remove(temp);
            }
            notifyAll();
            return personRequest;
        }
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
        return splitPolls.isEmpty();
    }

}
