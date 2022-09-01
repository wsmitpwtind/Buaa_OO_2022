import com.oocourse.uml2.models.common.Direction;
import com.oocourse.uml2.models.common.NameableType;
import com.oocourse.uml2.models.common.ReferenceType;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.common.NamedType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyOperation {
    private final String id;
    private final String name;
    private Visibility visibility;
    private NameableType outNameableType;
    private final ArrayList<NameableType> inNameableType;
    private List<String> outType;
    private List<String> inType;

    public MyOperation(String id, String name) {
        this.id = id;
        this.name = name;
        this.outNameableType = null;
        this.inNameableType = new ArrayList<>();
        initialType();
    }

    public String getId() {
        return id;
    }

    public void addVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public void initialType() {
        this.outType = new LinkedList<String>() {{
                add("byte");
                add("short");
                add("int");
                add("long");
                add("float");
                add("double");
                add("char");
                add("boolean");
                add("String");
                add("void");
            }};
        this.inType = new LinkedList<String>() {{
                add("byte");
                add("short");
                add("int");
                add("long");
                add("float");
                add("double");
                add("char");
                add("boolean");
                add("String");
            }};
    }

    public String getName() {
        return name;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void addNewParameter(Direction direction, NameableType nameableType) {
        if (direction == Direction.IN) {
            this.inNameableType.add(nameableType);
        }
        if (direction == Direction.RETURN) {
            this.outNameableType = nameableType;
        }
    }

    public boolean checkDuplicatedMethod(MyOperation operation) {
        ArrayList<NameableType> inNameableType2 = operation.inNameableType;
        if (inNameableType.size() == inNameableType2.size()) {
            for (NameableType type : inNameableType) {
                boolean isFound = false;

                for (NameableType type1 : inNameableType2) {
                    if (type.equals(type1)) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    return false;
                }
            }
            for (NameableType type1 : inNameableType2) {
                boolean isFound = false;
                for (NameableType type : inNameableType) {
                    if (type1.equals(type)) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int getClassOperationCouplingDegreeOperation(String inputId) {
        HashMap<String, Boolean> answer = new HashMap<>();
        answer.put(inputId, true);
        if (outNameableType != null) {
            if (outNameableType instanceof ReferenceType) {
                if (!answer.containsKey(((ReferenceType) outNameableType).getReferenceId())) {
                    answer.put(((ReferenceType) outNameableType).getReferenceId(), true);
                }
            }
        }
        for (NameableType type : inNameableType) {
            if (type instanceof ReferenceType) {
                if (!answer.containsKey(((ReferenceType) type).getReferenceId())) {
                    answer.put(((ReferenceType) type).getReferenceId(), true);
                }
            }
        }
        return answer.size() - 1;
    }

    public boolean checkWrongType() {
        if (outNameableType != null) {
            if (outNameableType instanceof NamedType) {
                if (!outType.contains(((NamedType) outNameableType).getName()))
                {
                    return true;
                }
            }
        }
        for (NameableType nameableType : inNameableType) {
            if (nameableType instanceof NamedType) {
                if (!inType.contains(((NamedType) nameableType).getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
