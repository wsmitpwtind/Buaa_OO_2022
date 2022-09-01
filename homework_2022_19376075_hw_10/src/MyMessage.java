import com.oocourse.spec2.main.Group;
import com.oocourse.spec2.main.Message;
import com.oocourse.spec2.main.Person;

public class MyMessage implements Message {
    private final int id;
    private final int socialValue;
    private final int type;
    private final MyPerson person1;
    private final MyPerson person2;
    private final MyGroup group;

    public MyMessage(int messageId, int messageSocialValue
            , Person messagePerson1, Person messagePerson2) {
        id = messageId;
        socialValue = messageSocialValue;
        type = 0;
        person1 = (MyPerson) messagePerson1;
        person2 = (MyPerson) messagePerson2;
        group = null;
    }

    public MyMessage(int messageId, int messageSocialValue
            , Person messagePerson1, Group messageGroup) {
        id = messageId;
        socialValue = messageSocialValue;
        type = 1;
        person1 = (MyPerson) messagePerson1;
        person2 = null;
        group = (MyGroup) messageGroup;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSocialValue() {
        return socialValue;
    }

    @Override
    public MyPerson getPerson1() {
        return person1;
    }

    @Override
    public MyPerson getPerson2() {
        return person2;
    }

    @Override
    public MyGroup getGroup() {
        return group;
    }
}
