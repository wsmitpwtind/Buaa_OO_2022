import com.oocourse.spec1.main.Group;
import com.oocourse.spec1.main.Network;
import com.oocourse.spec1.main.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyNetwork implements Network {
    private final Map<Integer, MyPerson> people;
    private final Map<Integer, Group> groups;
    private final ArrayList<MyPerson> blocks;

    public MyNetwork() {
        people = new HashMap<>();
        groups = new HashMap<>();
        blocks = new ArrayList<>();
    }

    @Override
    public boolean contains(int id) {
        return people.containsKey(id);
    }

    @Override
    public Person getPerson(int id) {
        return people.get(id);
    }

    @Override
    public void addPerson(Person person) throws MyEqualPersonIdException {
        if (person == null) {
            return;
        }
        if (people.containsKey(person.getId())) {
            throw new MyEqualPersonIdException(person.getId());
        }
        people.put(person.getId(), (MyPerson) person);
        blocks.add((MyPerson) person);
    }

    @Override
    public void addRelation(int id1, int id2, int value)
            throws MyPersonIdNotFoundException, MyEqualRelationException {
        if (!contains(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!contains(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else if (getPerson(id1).isLinked(getPerson(id2))) {
            throw new MyEqualRelationException(id1, id2);
        }
        if (id1 == id2 && contains(id1)) {
            return;
        }
        MyPerson p1 = (MyPerson) getPerson(id1);
        MyPerson p2 = (MyPerson) getPerson(id2);
        (p1).addAcquaintance(p2, value);
        (p2).addAcquaintance(p1, value);
        if (p1.getLeader() != p2.getLeader()) {
            if (p1.getLeader().getFellowsNum() < p2.getLeader().getFellowsNum()) {
                int flag = 0;
                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i).getId() == p1.getLeader().getId()) {
                        flag = i;
                        break;
                    }
                }
                p2.getLeader().transFellows(p1.getLeader());
                blocks.remove(flag);
            } else {
                int flag = 0;
                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i).getId() == p2.getLeader().getId()) {
                        flag = i;
                        break;
                    }
                }
                p1.getLeader().transFellows(p2.getLeader());
                blocks.remove(flag);
            }
        }
    }

    @Override
    public int queryValue(int id1, int id2)
            throws MyPersonIdNotFoundException, MyRelationNotFoundException {
        if (!contains(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!contains(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else if (!getPerson(id1).isLinked(getPerson(id2))) {
            throw new MyRelationNotFoundException(id1, id2);
        }
        return getPerson(id1).queryValue(getPerson(id2));
    }

    @Override
    public int queryPeopleSum() {
        return people.size();
    }

    @Override
    public boolean isCircle(int id1, int id2) throws MyPersonIdNotFoundException {
        if (!contains(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!contains(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        }
        if (id1 == id2) {
            return true;
        }
        MyPerson p1 = (MyPerson) getPerson(id1);
        MyPerson p2 = (MyPerson) getPerson(id2);
        return p1.getLeader().getId() == p2.getLeader().getId();
    }

    @Override
    public void addGroup(Group group) throws MyEqualGroupIdException {
        if (group == null) {
            return;
        }
        if (groups.containsKey(group.getId())) {
            throw new MyEqualGroupIdException(group.getId());
        }
        groups.put(group.getId(), group);

    }

    @Override
    public Group getGroup(int id) {
        return groups.get(id);
    }

    @Override
    public void addToGroup(int id1, int id2) throws MyGroupIdNotFoundException,
            MyPersonIdNotFoundException, MyEqualPersonIdException {
        if (!groups.containsKey(id2)) {
            throw new MyGroupIdNotFoundException(id2);
        }
        if (!people.containsKey(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        }
        Group g = groups.get(id2);
        Person p = people.get(id1);
        if (g.hasPerson(p)) {
            throw new MyEqualPersonIdException(id1);
        }
        if (((MyGroup) g).getPeopleSum() >= 1111) {
            return;
        }
        g.addPerson(p);
    }

    @Override
    public void delFromGroup(int id1, int id2) throws MyGroupIdNotFoundException,
            MyPersonIdNotFoundException, MyEqualPersonIdException {
        if (!groups.containsKey(id2)) {
            throw new MyGroupIdNotFoundException(id2);
        }
        if (!people.containsKey(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        }
        Group g = groups.get(id2);
        Person p = people.get(id1);
        if (!g.hasPerson(p)) {
            throw new MyEqualPersonIdException(id1);
        }
        g.delPerson(p);
    }

    @Override
    public int queryBlockSum() {
        return blocks.size();
    }
}