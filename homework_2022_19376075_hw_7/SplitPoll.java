import com.oocourse.elevator3.PersonRequest;

import java.util.Vector;

public class SplitPoll {
    private final Vector<PersonRequest> personRequests;
    private final int personID;
    private double minCost;
    private PersonRequestSet best;
    private final Vector<ElevatorProcessPool> elevatorProcesses;

    public SplitPoll(PersonRequest personRequest, Vector<ElevatorProcessPool> elevatorProcesses) {
        personRequests = new Vector<>();
        personRequests.add(personRequest);
        personID = personRequest.getPersonId();
        this.elevatorProcesses = elevatorProcesses;
        split(elevatorProcesses);
    }

    public int getPersonID() {
        return personID;
    }

    public int getSize() {
        return personRequests.size();
    }

    private void search(char nowBuilding
            , int nowFloor
            , char tarBuilding
            , int tarFloor
            , PersonRequestSet personRequestSet
            , Vector<ElevatorProcessPool> elevatorProcesses) {
        if (nowBuilding == tarBuilding && nowFloor == tarFloor) {
            double cost = personRequestSet.cal(elevatorProcesses);
            if (cost < minCost) {
                best = personRequestSet;
                minCost = cost;
            }
            return;
        }
        for (int i = 1; i <= 10; i++) {
            boolean empty = elevatorProcesses.get(i + 4).isEmpty(nowBuilding);
            if ((!empty || i == tarFloor) && i != nowFloor) {
                boolean in = false;
                for (PersonRequest personRequest : personRequestSet.getPersonRequestSet()) {
                    char fb = personRequest.getFromBuilding();
                    int ff = personRequest.getFromFloor();
                    if (fb == nowBuilding && ff == i) {
                        in = true;
                        break;
                    }
                }
                if (!in) {
                    PersonRequest temp = new PersonRequest(nowFloor
                            , i, nowBuilding, nowBuilding, personID);
                    personRequestSet.add(temp);
                    search(nowBuilding, i, tarBuilding
                            , tarFloor, new PersonRequestSet(personRequestSet), elevatorProcesses);
                    personRequestSet.remove(temp);
                }
            }
        }

        for (int i = 0; i <= 4; i++) {
            if (i != nowBuilding - 'A') {
                Vector<ElevatorProcess> temp = new Vector<>(elevatorProcesses.get(nowFloor + 4)
                        .getElevatorProcesses());
                for (ElevatorProcess elevatorProcess : temp) {
                    if ((elevatorProcess.getSwitchInfo() & (1 << i)) != 0) {
                        boolean in = false;
                        for (PersonRequest personRequest : personRequestSet.getPersonRequestSet()) {
                            if (personRequest.getFromBuilding() == (char) (i + 'A') && personRequest
                                    .getFromFloor() == nowFloor) {
                                in = true;
                                break;
                            }
                        }
                        if (!in) {
                            PersonRequest tempP = new PersonRequest(nowFloor
                                    , nowFloor, nowBuilding, (char) (i + 'A'), personID);
                            personRequestSet.add(tempP);
                            search((char) (i + 'A')
                                    , nowFloor, tarBuilding, tarFloor
                                    , new PersonRequestSet(personRequestSet), elevatorProcesses);
                            personRequestSet.remove(tempP);
                        }
                    }
                }
            }
        }
    }

    public synchronized void split(Vector<ElevatorProcessPool> elevatorProcesses) {
        PersonRequest personRequest = personRequests.get(0);
        PersonRequestSet personRequestSet = new PersonRequestSet();
        minCost = Integer.MAX_VALUE;
        if (personRequest.getFromBuilding() == personRequest.getToBuilding())
        {
            notifyAll();
        } else if (personRequest.getFromFloor() == personRequest.getToFloor())
        {
            notifyAll();
        } else {
            int[] cont = new int[]{10, 9, 2, 8, 3, 7, 4, 6, 5, 1};
            int max = Integer.MAX_VALUE;
            int tar = 1;
            for (Integer i : cont) {
                boolean accept = false;
                for (ElevatorProcess elevatorProcess : elevatorProcesses
                        .get(i + 4).getElevatorProcesses()) {
                    int code1 = (1 << (personRequest.getFromBuilding() - 'A'));
                    int code2 = (1 << (personRequest.getToBuilding() - 'A'));
                    if ((elevatorProcess.getSwitchInfo() & code1) != 0 && (elevatorProcess.
                            getSwitchInfo() & code2) != 0) {
                        accept = true;
                        break;
                    }
                }
                int cost;
                if (accept) {
                    cost = Math.abs(personRequest.getFromFloor() - i) + Math.
                            abs(personRequest.getToFloor() - i);
                    if (cost < max) {
                        max = cost;
                        tar = i;
                    }
                }
            }

            personRequests.remove(0);
            //personRequests.addAll(best.getPersonRequestSet());
            personRequests.add(new PersonRequest(
                    personRequest.getFromFloor()
                    , tar
                    , personRequest.getFromBuilding()
                    , personRequest.getFromBuilding()
                    , personID));
            personRequests.add(new PersonRequest(
                    tar
                    , tar
                    , personRequest.getFromBuilding()
                    , personRequest.getToBuilding()
                    , personID));
            personRequests.add(new PersonRequest(
                    tar
                    , personRequest.getToFloor()
                    , personRequest.getToBuilding()
                    , personRequest.getToBuilding()
                    , personID));

            notifyAll();
        }
    }

    public synchronized PersonRequest finishStage() {
        if (personRequests.isEmpty()) {
            notifyAll();
            return null;
        } else {
            final PersonRequest personRequest = personRequests.get(0);
            personRequests.remove(0);
            notifyAll();
            return personRequest;
        }
    }
}
