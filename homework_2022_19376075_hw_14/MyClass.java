import com.oocourse.uml2.models.common.NameableType;
import com.oocourse.uml2.models.common.ReferenceType;
import com.oocourse.uml2.models.common.Visibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyClass {
    private final String id;
    private final String name;
    private MyClass father;
    private final HashMap<String, MyClass> sons;
    private Integer sonSize;
    private final HashMap<String, MyInterface> interfaces;
    private final HashMap<String, MyAttribute> attributes;
    private final HashMap<String, MyOperation> operations;

    public MyClass(String id, String name) {
        this.id = id;
        this.name = name;
        father = null;
        sons = new HashMap<>();
        sonSize = 0;
        interfaces = new HashMap<>();
        attributes = new HashMap<>();
        operations = new HashMap<>();
    }

    public void addFamily(MyClass myClass, String relationship) {
        switch (relationship) {
            case "father":
                father = myClass;
                return;
            case "son":
                sons.put(myClass.getId(), myClass);
                sonSize++;
                return;
            default:
        }
    }

    public void addInterface(MyInterface myInterface) {
        interfaces.put(myInterface.getId(), myInterface);
    }

    public void addAttribute(MyAttribute myAttribute) {
        attributes.put(myAttribute.getId(), myAttribute);
    }

    public void addOperation(MyOperation myOperation) {
        operations.put(myOperation.getId(), myOperation);
    }

    public String getId() {
        return id;
    }

    public boolean hasWrongType(String name) {
        for (MyOperation myOperation : operations.values()) {
            if (myOperation.getName().equals(name)) {
                if (myOperation.checkWrongType()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasDuplicatedMethod(String name) {
        ArrayList<MyOperation> operationList = new ArrayList<>();

        for (MyOperation myOperation : operations.values()) {
            if (myOperation.getName().equals(name)) {
                operationList.add(myOperation);
            }
        }

        for (int i = 0; i < operationList.size() - 1; i++) {
            for (int j = i + 1; j < operationList.size(); j++) {
                MyOperation operation1 = operationList.get(i);
                MyOperation operation2 = operationList.get(j);
                if (operation1.checkDuplicatedMethod(operation2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public int getClassSubClassCount() {
        return sonSize;
    }

    public int getClassOperationCount() {
        return operations.size();
    }

    public Map<Visibility, Integer> getClassOperationVisibility(String name) {
        HashMap<Visibility, Integer> ans = new HashMap<>();

        for (MyOperation myOperation : operations.values()) {
            if (myOperation.getName().equals(name)) {
                if (ans.containsKey(myOperation.getVisibility())) {
                    ans.put(myOperation.getVisibility(), ans.get(myOperation.getVisibility()) + 1);
                } else if (myOperation.getVisibility() == Visibility.PUBLIC) {
                    ans.put(Visibility.PUBLIC, 1);
                } else if (myOperation.getVisibility() == Visibility.PROTECTED) {
                    ans.put(Visibility.PROTECTED, 1);
                } else if (myOperation.getVisibility() == Visibility.PRIVATE) {
                    ans.put(Visibility.PRIVATE, 1);
                } else if (myOperation.getVisibility() == Visibility.PACKAGE) {
                    ans.put(Visibility.PACKAGE, 1);
                }
            }
        }
        if (!ans.containsKey(Visibility.PUBLIC)) {
            ans.put(Visibility.PUBLIC, 0);
        }
        if (!ans.containsKey(Visibility.PROTECTED)) {
            ans.put(Visibility.PROTECTED, 0);
        }
        if (!ans.containsKey(Visibility.PRIVATE)) {
            ans.put(Visibility.PRIVATE, 0);
        }
        if (!ans.containsKey(Visibility.PACKAGE)) {
            ans.put(Visibility.PACKAGE, 0);
        }

        return ans;
    }

    public ArrayList<Integer> getOperationCouplingDegree(String name) {
        ArrayList<Integer> ans = new ArrayList<>();
        for (MyOperation myOperation : operations.values()) {
            if (myOperation.getName().equals(name)) {
                ans.add(myOperation.getClassOperationCouplingDegreeOperation(id));
            }
        }
        return ans;
    }

    private int getClassAttributeCouplingDegreeClass(String inputId, ArrayList<String> ansList) {
        int tempAns = 0;
        ArrayList<String> thisList = new ArrayList<>(ansList);
        if (!thisList.contains(inputId)) {
            thisList.add(inputId);
        }

        for (MyAttribute myAttribute : attributes.values()) {
            NameableType nameableType = myAttribute.getType();
            if (nameableType instanceof ReferenceType) {
                if (!thisList.contains(((ReferenceType) nameableType).getReferenceId())) {
                    thisList.add(((ReferenceType) nameableType).getReferenceId());
                    tempAns++;
                }
            }
        }
        if (father != null) {
            tempAns = tempAns + father.getClassAttributeCouplingDegreeClass(inputId, thisList);
        }
        return tempAns;
    }

    public int getAttributeCouplingDegreePublic(String inputId)
    {
        return getClassAttributeCouplingDegreeClass(inputId, new ArrayList<>());
    }

    public ArrayList<String> getImplementInterfaceList() {
        ArrayList<String> tempAns = new ArrayList<>();
        if (father != null) {
            tempAns.addAll(father.getImplementInterfaceList());
        }
        for (MyInterface myInterface : interfaces.values()) {
            tempAns.addAll(myInterface.getClassImplementInterfaceListFather());
        }
        return tempAns;
    }

    private int getClassDepthOfInheritance() {
        if (father != null) {
            return father.getClassDepthOfInheritance() + 1;
        } else {
            return 0;
        }
    }

    public int outerGetClassDepthOfInheritance() {
        return getClassDepthOfInheritance();
    }
}
