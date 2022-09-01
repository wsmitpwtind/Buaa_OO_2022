import com.oocourse.elevator3.PersonRequest;

import java.util.Vector;

public class PersonRequestSet {
    private final Vector<PersonRequest> personRequestSet;

    public PersonRequestSet() {
        this.personRequestSet = new Vector<>();
    }

    public PersonRequestSet(PersonRequestSet personRequestSet) {
        this.personRequestSet = new Vector<>(personRequestSet.getPersonRequestSet());
    }

    public Vector<PersonRequest> getPersonRequestSet() {
        return personRequestSet;
    }

    public void add(PersonRequest personRequest) {
        personRequestSet.add(personRequest);
    }

    public void remove(PersonRequest personRequest) {
        personRequestSet.remove(personRequest);
    }

    public double cal(Vector<ElevatorProcessPool> elevatorProcesses) {
        double answer = 0;
        double temp;
        for (PersonRequest personRequest : personRequestSet) {
            if (personRequest.getFromBuilding() == personRequest.getToBuilding()) {
                temp = elevatorProcesses.get(personRequest.getFromBuilding() - 'A').getCost();
                int len = Math.abs(personRequest.getFromFloor() - personRequest.getToFloor());
                answer = answer + temp * len + 400;
            } else {
                char fb = personRequest.getFromBuilding();
                char tb = personRequest.getToBuilding();
                temp = elevatorProcesses.get(personRequest.getFromFloor() + 4).getCost(fb, tb);
                int len = Math.abs(fb - tb);
                answer = answer + temp * len + 400;
            }
        }
        return answer;
    }
}
