import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class MyPerson implements Person {
    private final int id;
    private final String name;
    private final int age;
    private final Map<Integer, MyPerson> acquaintanceMap;

    private final Map<Integer, Integer> value;
    private int money;
    private int socialValue;
    private final List<Message> messages;

    private final ArrayList<MyPerson> fellows;
    private MyPerson leader;
    private int blockValue;

    private final Map<Integer, Map<Integer, Integer>> leaderAcquaintanceMap;

    private Map<MyPair, Integer> tree;
    private MyPair last;

    public MyPerson(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        acquaintanceMap = new HashMap<>();
        value = new HashMap<>();
        fellows = new ArrayList<>();
        fellows.add(this);
        leader = this;
        blockValue = 0;
        leaderAcquaintanceMap = new HashMap<>();
        messages = new ArrayList<>();
    }

    public void reGenerateLeaderAcquaintanceMap(int id1, int id2, int value) {
        tree = new HashMap<>();
        ArrayList<Integer> tempArr = new ArrayList<>();
        tempArr.add(id1);
        search(id1, id2, tempArr);
        if (leaderAcquaintanceMap.get(last.getKey()).get(last.getValue()) > value) {
            blockValue -= leaderAcquaintanceMap.get(last.getKey()).get(last.getValue());
            if (leaderAcquaintanceMap.get(last.getKey()).size() != 1) {
                leaderAcquaintanceMap.get(last.getKey()).remove(last.getValue());
            } else {
                leaderAcquaintanceMap.remove(last.getKey());
            }

            if (leaderAcquaintanceMap.get(last.getValue()).size() != 1) {
                leaderAcquaintanceMap.get(last.getValue()).remove(last.getKey());
            } else {
                leaderAcquaintanceMap.remove(last.getValue());
            }

            if (leaderAcquaintanceMap.containsKey(id1)) {
                leaderAcquaintanceMap.get(id1).put(id2, value);
            } else {
                Map<Integer, Integer> temp = new HashMap<>();
                temp.put(id2, value);
                leaderAcquaintanceMap.put(id1, temp);
            }

            if (leaderAcquaintanceMap.containsKey(id2)) {
                leaderAcquaintanceMap.get(id2).put(id1, value);
            } else {
                Map<Integer, Integer> temp = new HashMap<>();
                temp.put(id1, value);
                leaderAcquaintanceMap.put(id2, temp);
            }

            blockValue += value;
        }
    }

    private void search(int id1, int id2, List<Integer> visited) {
        if (id1 == id2) {
            int max = -1;
            MyPair tar = null;
            for (MyPair pair : tree.keySet()) {
                if (tree.get(pair) > max) {
                    max = tree.get(pair);
                    tar = pair;
                }
            }
            last = tar;
        } else {
            Map<Integer, Integer> map = leaderAcquaintanceMap.get(id1);
            for (Integer code : map.keySet()) {
                if (!visited.contains(code)) {
                    visited.add(code);
                    MyPair pair = new MyPair(id1, code);
                    tree.put(pair, map.get(code));
                    search(code, id2, visited);
                    tree.remove(pair);
                    visited.remove((Integer) code);
                }
            }
        }
    }

    public Map<Integer, Map<Integer, Integer>> getLeaderAcquaintanceMap() {
        return leaderAcquaintanceMap;
    }

    public int getBlockValue() {
        return blockValue;
    }

    public void setBlockValue(int blockValue) {
        this.blockValue = blockValue;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Person)) {
            return false;
        }
        return ((Person) obj).getId() == id;
    }

    @Override
    public boolean isLinked(Person person) {
        if (acquaintanceMap.containsKey(person.getId())) {
            return true;
        }
        return id == person.getId();
    }

    public void addAcquaintance(MyPerson p, Integer v) {
        acquaintanceMap.put(p.getId(), p);
        value.put(p.getId(), v);
    }

    @Override
    public int queryValue(Person person) {
        Integer v = value.get(person.getId());
        if (v != null) {
            return v;
        }
        return 0;
    }

    @Override
    public int compareTo(Person p2) {
        return name.compareTo(p2.getName());
    }

    @Override
    public void addSocialValue(int num) {
        socialValue += num;
    }

    @Override
    public int getSocialValue() {
        return socialValue;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public List<Message> getReceivedMessages() {
        if (messages.size() <= 4) {
            return messages;
        } else {
            return messages.subList(0, 4);
        }
    }

    public void addFirstMessage(MyMessage message) {
        messages.add(0, message);
    }

    @Override
    public void addMoney(int num) {
        money += num;
    }

    @Override
    public int getMoney() {
        return money;
    }

    @Override
    public int hashCode() {
        return (new Integer(id)).hashCode();
    }

    public Iterator<MyPerson> getAcquaintanceIter() {
        return acquaintanceMap.values().iterator();
    }

    public MyPerson getLeader() {
        return leader;
    }

    public void setLeader(MyPerson person) {
        leader = person;
    }

    public void addFellows(ArrayList<MyPerson> fellows) {
        this.fellows.addAll(fellows);
        for (MyPerson person : fellows) {
            person.setLeader(leader);
        }
    }

    public void addLeaderAcquaintanceMapAll(Map<Integer
            , Map<Integer, Integer>> leaderAcquaintanceMap) {
        this.leaderAcquaintanceMap.putAll(leaderAcquaintanceMap);
    }

    public void addLeaderAcquaintanceMapOne(int id1, int id2, int value) {
        if (leaderAcquaintanceMap.containsKey(id1)) {
            leaderAcquaintanceMap.get(id1).put(id2, value);
        } else {
            Map<Integer, Integer> temp = new HashMap<>();
            temp.put(id2, value);
            this.leaderAcquaintanceMap.put(id1, temp);
        }
        if (leaderAcquaintanceMap.containsKey(id2)) {
            leaderAcquaintanceMap.get(id2).put(id1, value);
        } else {
            Map<Integer, Integer> temp = new HashMap<>();
            temp.put(id1, value);
            this.leaderAcquaintanceMap.put(id2, temp);
        }
    }

    public void delFellows() {
        int size = fellows.size();
        if (size > 0) {
            fellows.subList(0, size).clear();
        }
    }

    public void transFellows(MyPerson person) {
        this.addFellows(person.fellows);
        person.delFellows();
        person.leader = this;
    }

    public int getFellowsNum() {
        return fellows.size();
    }

    public Map<Integer, MyPerson> getAcquaintanceMap() {
        return acquaintanceMap;
    }

    public ArrayList<MyPerson> getFellows() {
        return fellows;
    }

    public Map<Integer, Integer> getValue() {
        return value;
    }
}