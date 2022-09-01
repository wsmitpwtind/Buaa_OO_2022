import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.Network;
import com.oocourse.spec3.main.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;

public class MyNetwork implements Network {
    private final Map<Integer, MyPerson> people;
    private final Map<Integer, MyGroup> groups;
    private final Map<Integer, MyMessage> messages;
    private final Map<Integer, Integer> emojiHeatList;
    private final ArrayList<MyPerson> blocks;

    public MyNetwork() {
        people = new HashMap<>();
        groups = new HashMap<>();
        messages = new HashMap<>();
        emojiHeatList = new HashMap<>();
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
        return messages.containsKey(id);
    }

    @Override
    public void addMessage(Message message) throws MyEqualMessageIdException
            , MyEqualPersonIdException, MyEmojiIdNotFoundException {
        if (message == null) {
            return;
        }
        if (messages.containsKey(message.getId())) {
            throw new MyEqualMessageIdException(message.getId());
        } else if (message instanceof MyEmojiMessage && !containsEmojiId(
                ((MyEmojiMessage) message).getEmojiId())) {
            throw new MyEmojiIdNotFoundException(((MyEmojiMessage) message).getEmojiId());
        } else if (message.getType() == 0 && message.getPerson1() == message.getPerson2()) {
            throw new MyEqualPersonIdException(message.getPerson1().getId());
        }
        if (message instanceof MyEmojiMessage) {
            messages.put(message.getId(), (MyEmojiMessage) message);
        } else if (message instanceof MyNoticeMessage) {
            messages.put(message.getId(), (MyNoticeMessage) message);
        } else if (message instanceof MyRedEnvelopeMessage) {
            messages.put(message.getId(), (MyRedEnvelopeMessage) message);
        } else {
            messages.put(message.getId(), (MyMessage) message);
        }
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
            if (message instanceof MyRedEnvelopeMessage) {
                person1.addMoney(-((MyRedEnvelopeMessage) message).getMoney());
                person2.addMoney(+((MyRedEnvelopeMessage) message).getMoney());
            }
            messages.remove(id);
            person2.addFirstMessage(message);
        }
        if (message.getType() == 1) {
            MyGroup group = message.getGroup();
            group.addSocialValue(message.getSocialValue());
            if (message instanceof MyRedEnvelopeMessage) {
                int money = ((MyRedEnvelopeMessage) message).getMoney() / group.getSize();
                person1.addMoney(- group.getSize() * money);
                group.addPeopleMoney(money);
            }
            messages.remove(id);
        }
        if (message instanceof MyEmojiMessage) {
            if (emojiHeatList.containsKey(((MyEmojiMessage) message).getEmojiId())) {
                emojiHeatList.put(((MyEmojiMessage) message).getEmojiId()
                        , emojiHeatList.get(((MyEmojiMessage) message).getEmojiId()) + 1);
            }
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
    public List<Message> queryReceivedMessages(int id) throws MyPersonIdNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            return people.get(id).getReceivedMessages();
        }
    }

    @Override
    public boolean containsEmojiId(int id) {
        return emojiHeatList.containsKey(id);
    }

    @Override
    public void storeEmojiId(int id) throws MyEqualEmojiIdException {
        if (containsEmojiId(id)) {
            throw new MyEqualEmojiIdException(id);
        } else {
            emojiHeatList.put(id, 0);
        }
    }

    @Override
    public int queryMoney(int id) throws MyPersonIdNotFoundException {
        if (!contains(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            return getPerson(id).getMoney();
        }
    }

    @Override
    public int queryPopularity(int id) throws MyEmojiIdNotFoundException {
        if (!containsEmojiId(id)) {
            throw new MyEmojiIdNotFoundException(id);
        } else {
            return emojiHeatList.get(id);
        }
    }

    @Override
    public int deleteColdEmoji(int limit) {
        Map<Integer, MyMessage> temp = new HashMap<>(messages);
        Map<Integer, Integer> tempEmoji = new HashMap<>(emojiHeatList);
        for (MyMessage message : temp.values()) {
            if (message instanceof MyEmojiMessage) {
                if (tempEmoji.containsKey(((MyEmojiMessage) message).getEmojiId())) {
                    if (tempEmoji.get(((MyEmojiMessage) message).getEmojiId()) < limit) {
                        emojiHeatList.remove(((MyEmojiMessage) message).getEmojiId());
                        messages.remove(message.getId());
                    }
                }
            }
        }
        tempEmoji = new HashMap<>(emojiHeatList);
        for (Integer emojiId : tempEmoji.keySet())
        {
            if (emojiHeatList.get(emojiId) < limit) {
                emojiHeatList.remove(emojiId);
            }
        }
        return emojiHeatList.size();
    }

    @Override
    public void clearNotices(int personId) throws MyPersonIdNotFoundException {
        if (!contains(personId)) {
            throw new MyPersonIdNotFoundException(personId);
        } else {
            ArrayList<Message> temp = new ArrayList<>(people.get(personId).getMessages());
            for (Message message : temp) {
                if (message instanceof MyNoticeMessage) {
                    people.get(personId).getMessages().remove(message);
                }
            }
        }
    }

    @Override
    public int sendIndirectMessage(int id) throws MyMessageIdNotFoundException {
        if (!containsMessage(id) || getMessage(id).getType() == 1) {
            throw new MyMessageIdNotFoundException(id);
        } else {
            MyMessage message = messages.get(id);
            MyPerson p1 = (MyPerson) getPerson(message.getPerson1().getId());
            MyPerson p2 = (MyPerson) getPerson(message.getPerson2().getId());
            if (p1.getLeader().getId() != p2.getLeader().getId()) {
                return -1;
            } else {
                messages.remove((Integer) id);
                p1.addSocialValue(message.getSocialValue());
                p2.addSocialValue(message.getSocialValue());
                p2.addFirstMessage(message);
                if (message instanceof MyRedEnvelopeMessage) {
                    p1.addMoney(-((MyRedEnvelopeMessage) message).getMoney());
                    p2.addMoney(+((MyRedEnvelopeMessage) message).getMoney());
                } else if (message instanceof MyEmojiMessage) {
                    if (emojiHeatList.containsKey(((MyEmojiMessage) message).getEmojiId())) {
                        emojiHeatList.put(((MyEmojiMessage) message).getEmojiId()
                                , emojiHeatList.get(((MyEmojiMessage) message).getEmojiId()) + 1);
                    }
                }
                return dijkstraMain(p1, p2);
            }
        }
    }

    @Override
    public int queryBlockSum() {
        return blocks.size();
    }

    @Override
    public int queryLeastConnection(int id) throws MyPersonIdNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            return people.get(id).getLeader().getBlockValue();
        }
    }

    private int dijkstraMain(MyPerson person1, MyPerson person2) {
        class Edge {
            private final int to;
            private final int len;

            Edge(int to, int len) {
                this.to = to;
                this.len = len;
            }
        }

        MyPerson leader = person1.getLeader();

        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Edge) o1).len - ((Edge) o2).len;
            }
        });

        Map<Integer, Boolean> visited = new HashMap<>();
        //源点 到某个点的距离
        Map<Integer, Integer> dist = new HashMap<>();


        pq.offer(new Edge(person1.getId(), 0));

        for (MyPerson person : leader.getFellows()) {
            visited.put(person.getId(), false);
        }
        for (MyPerson person : leader.getFellows()) {
            dist.put(person.getId(), Integer.MAX_VALUE);
        }
        dist.put(person1.getId(), 0);

        while (!pq.isEmpty()) {
            //目的地
            int u = pq.poll().to;

            if (visited.get(u)) {
                continue;
            }
            visited.put(u, true);

            //遍历这个点的所有通路
            for (Integer acquaintanceId : people.get(u).getAcquaintanceMap().keySet()) {
                int v = acquaintanceId;
                int w = people.get(u).getValue().get(acquaintanceId);

                if (dist.get(v) > dist.get(u) + w) {
                    dist.put(v, dist.get(u) + w);
                    pq.offer(new Edge(v, dist.get(v)));
                }
            }
        }
        return dist.get(person2.getId());
    }
}