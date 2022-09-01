import com.oocourse.spec2.main.Group;
import com.oocourse.spec2.main.Person;

import java.util.HashMap;
import java.util.Map;

public class MyGroup implements Group {
    private final int id;
    private final Map<Integer, MyPerson> people;
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
        people.put(person.getId(), (MyPerson) person);
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
        for (MyPerson p1 : people.values()) {
            for (MyPerson p2 : p1.getAcquaintanceMap().values()) {
                if (people.containsKey(p2.getId())) {
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

    public void addSocialValue(int value) {
        for (MyPerson myPerson : people.values()) {
            myPerson.addSocialValue(value);
        }
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