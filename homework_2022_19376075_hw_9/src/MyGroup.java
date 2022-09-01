import com.oocourse.spec1.main.Group;
import com.oocourse.spec1.main.Person;

import java.util.HashMap;
import java.util.Map;

public class MyGroup implements Group {
    private final int id;
    private final Map<Integer, Person> people;
    private int sumAge;
    private int sumAgeSquare;

    public MyGroup(int id) {
        this.id = id;
        this.people = new HashMap<>();
        this.sumAge = 0;
        this.sumAgeSquare = 0;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Group)) {
            return false;
        }
        return ((Group) obj).getId() == id;
    }

    @Override
    public void addPerson(Person person) {
        people.put(person.getId(), person);
        sumAge += person.getAge();
        sumAgeSquare += person.getAge() * person.getAge();
    }

    @Override
    public void delPerson(Person person) {
        people.remove(person.getId());
        sumAge -= person.getAge();
        sumAgeSquare -= person.getAge() * person.getAge();
    }

    @Override
    public int getSize() {
        return people.size();
    }

    @Override
    public boolean hasPerson(Person person) {
        if (person == null) {
            return false;
        }
        return people.containsKey(person.getId());
    }

    @Override
    public int getValueSum() {
        int sum = 0;
        for (Person p1 : people.values()) {
            for (Person p2 : people.values()) {
                if (p1.isLinked(p2)) {
                    sum += p1.queryValue(p2);
                }
            }
        }
        return sum;
    }

    @Override
    public int getAgeMean() {
        if (people.size() == 0) {
            return 0;
        }
        return sumAge / people.size();
    }

    @Override
    public int getAgeVar() {
        if (people.size() == 0) {
            return 0;
        }
        int mean = sumAge / people.size();
        return (sumAgeSquare - 2 * sumAge * mean + people.size() * mean * mean) / people.size();
    }

    public int getPeopleSum() {
        return people.size();
    }

}