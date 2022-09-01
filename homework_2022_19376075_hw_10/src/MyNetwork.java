import com.oocourse.spec2.exceptions.PersonIdNotFoundException;
import com.oocourse.spec2.main.Group;
import com.oocourse.spec2.main.Message;
import com.oocourse.spec2.main.Network;
import com.oocourse.spec2.main.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyNetwork implements Network {
    private final Map<Integer, MyPerson> people;
    private final Map<Integer, MyGroup> groups;
    private final Map<Integer, MyMessage> messages;
    private final ArrayList<MyPerson> blocks;

    public MyNetwork() {
        people = new HashMap<>();
        groups = new HashMap<>();
        messages = new HashMap<>();
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
                p2.getLeader().setBlockValue(p2.getLeader().getBlockValue()
                        + p1.getLeader().getBlockValue() + value);
                p2.getLeader().addLeaderAcquaintanceMapAll(
                        p1.getLeader().getLeaderAcquaintanceMap());
                p2.getLeader().addLeaderAcquaintanceMapOne(id1, id2, value);
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
                p1.getLeader().setBlockValue(p1.getLeader().getBlockValue()
                        + p2.getLeader().getBlockValue() + value);
                p1.getLeader().addLeaderAcquaintanceMapAll(
                        p2.getLeader().getLeaderAcquaintanceMap());
                p1.getLeader().addLeaderAcquaintanceMapOne(id1, id2, value);
                p1.getLeader().transFellows(p2.getLeader());
                blocks.remove(flag);
            }
        } else {
            p1.getLeader().reGenerateLeaderAcquaintanceMap(id1, id2, value);
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
        groups.put(group.getId(), (MyGroup) group);

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
        MyGroup g = groups.get(id2);
        Person p = people.get(id1);
        if (g.hasPerson(p)) {
            throw new MyEqualPersonIdException(id1);
        }
        if (g.getPeopleSum() >= 1111) {
            return;
        }
        g.addPerson(p);
    }

    @Override
    public int queryGroupPeopleSum(int id) throws MyGroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new MyGroupIdNotFoundException(id);
        } else {
            return groups.get(id).getSize();
        }
    }

    @Override
    public int queryGroupValueSum(int id) throws MyGroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new MyGroupIdNotFoundException(id);
        } else {
            return groups.get(id).getValueSum();
        }
    }

    @Override
    public int queryGroupAgeVar(int id) throws MyGroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new MyGroupIdNotFoundException(id);
        } else {
            return groups.get(id).getAgeVar();
        }
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
    public boolean containsMessage(int id) {
        return groups.containsKey(id);
    }

    @Override
    public void addMessage(Message message) throws MyEqualMessageIdException
            , MyEqualPersonIdException {
        if (message == null) {
            return;
        }
        if (messages.containsKey(message.getId())) {
            throw new MyEqualMessageIdException(message.getId());
        } else if (message.getType() == 0 && message.getPerson1() == message.getPerson2()) {
            throw new MyEqualPersonIdException(message.getPerson1().getId());
        }
        messages.put(message.getId(), (MyMessage) message);
    }

    @Override
    public Message getMessage(int id) {
        return messages.getOrDefault(id, null);
    }

    @Override
    public void sendMessage(int id) throws MyRelationNotFoundException
            , MyMessageIdNotFoundException, MyPersonIdNotFoundException {
        if (!messages.containsKey(id)) {
            throw new MyMessageIdNotFoundException(id);
        } else if (messages.get(id).getType() == 0 && !messages.get(id).getPerson1()
                .isLinked(messages.get(id).getPerson2())) {
            throw new MyRelationNotFoundException(messages.get(id).getPerson1().getId()
                    , messages.get(id).getPerson2().getId());
        } else if (messages.get(id).getType() == 1 && !messages.get(id).getGroup()
                .hasPerson(messages.get(id).getPerson1())) {
            throw new MyPersonIdNotFoundException(messages.get(id).getPerson1().getId());
        }
        MyMessage message = messages.get(id);
        MyPerson person1 = message.getPerson1();
        if (message.getType() == 0) {
            MyPerson person2 = message.getPerson2();
            person1.addSocialValue(message.getSocialValue());
            person2.addSocialValue(message.getSocialValue());
            messages.remove(id);
            person2.addFirstMessage(message);
        }
        if (message.getType() == 1) {
            MyGroup group = message.getGroup();
            group.addSocialValue(message.getSocialValue());
            messages.remove(id);
        }
    }

    @Override
    public int querySocialValue(int id) throws MyPersonIdNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            return people.get(id).getSocialValue();
        }
    }

    @Override
    public List<Message> queryReceivedMessages(int id) throws PersonIdNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            return people.get(id).getReceivedMessages();
        }
    }

    @Override
    public int queryBlockSum() {
        return blocks.size();
    }

    @Override
    public int queryLeastConnection(int id) throws PersonIdNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            return people.get(id).getLeader().getBlockValue();
        }
    }
}