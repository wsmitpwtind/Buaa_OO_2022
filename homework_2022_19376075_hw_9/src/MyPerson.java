import com.oocourse.spec1.main.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyPerson implements Person {
    private final int id;
    private final String name;
    private final int age;
    private final Map<Integer, MyPerson> acquaintanceMap;
    private final Map<Integer, Integer> value;
    private final ArrayList<MyPerson> fellows;
    private MyPerson leader;

    public MyPerson(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        acquaintanceMap = new HashMap<>();
        value = new HashMap<>();
        fellows = new ArrayList<>();
        leader = this;
        fellows.add(this);
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

    public int getFellowsNum()
    {
        return fellows.size();
    }
}