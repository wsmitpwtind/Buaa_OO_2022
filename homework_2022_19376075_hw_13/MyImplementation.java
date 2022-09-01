import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.MethodDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.MethodWrongTypeException;
import com.oocourse.uml1.interact.format.UserApi;
import com.oocourse.uml1.models.common.ElementType;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlInterfaceRealization;
import com.oocourse.uml1.models.elements.UmlGeneralization;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlParameter;
import com.oocourse.uml1.models.elements.UmlAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyImplementation implements UserApi {
    private final HashMap<String, MyClass> idClassMap;
    private final HashMap<String, MyClass> nameClassMap;
    private final HashMap<String, MyInterface> idInterfaceMap;
    private final HashMap<String, MyOperation> idOperationMap;
    private final ArrayList<String> duplicatedClass;

    public MyImplementation(UmlElement... elements) {
        idOperationMap = new HashMap<>();
        idClassMap = new HashMap<>();
        idInterfaceMap = new HashMap<>();
        nameClassMap = new HashMap<>();
        duplicatedClass = new ArrayList<>();

        for (UmlElement umlElement : elements) {
            classInsert(umlElement);
        }

        for (UmlElement umlElement : elements) {
            interfaceInsert(umlElement);
        }

        for (UmlElement umlElement : elements) {
            generalizationInsert(umlElement);
        }

        for (UmlElement umlElement : elements) {
            interfaceRealizationInsert(umlElement);
        }

        for (UmlElement umlElement : elements) {
            attributeInsert(umlElement);
        }

        for (UmlElement umlElement : elements) {
            operationInsert(umlElement);
        }

        for (UmlElement umlElement : elements) {
            parameterInsert(umlElement);
        }
    }

    private void classInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_CLASS) {
            MyClass myClass = new MyClass(umlElement.getId(), umlElement.getName());
            idClassMap.put(umlElement.getId(), myClass);
            nameClassMapPut(umlElement.getName(), myClass);
        }
    }

    private void nameClassMapPut(String name, MyClass myClass) {
        if (nameClassMap.containsKey(name)) {
            duplicatedClass.add(name);
        } else {
            nameClassMap.put(name, myClass);
        }
    }

    public void interfaceInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_INTERFACE) {
            MyInterface myInterface = new MyInterface(umlElement.getId(), umlElement.getName());
            idInterfaceMap.put(umlElement.getId(), myInterface);
        }
    }

    private void generalizationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_GENERALIZATION) {
            if (idClassMap.containsKey(((UmlGeneralization) umlElement).getSource())) {
                MyClass mySon = idClassMap.get(((UmlGeneralization) umlElement).getSource());
                MyClass myFather = idClassMap.get(((UmlGeneralization) umlElement).getTarget());
                mySon.addFamily(myFather, "father");
                myFather.addFamily(mySon, "son");
            } else {
                MyInterface mySon = idInterfaceMap
                        .get(((UmlGeneralization) umlElement).getSource());
                MyInterface myFather = idInterfaceMap.get(((UmlGeneralization) umlElement)
                        .getTarget());
                mySon.addFamily(myFather, "father");
            }
        }
    }

    private void interfaceRealizationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_INTERFACE_REALIZATION) {
            MyClass son = idClassMap.get(((UmlInterfaceRealization) umlElement).getSource());
            MyInterface father =
                    idInterfaceMap.get(((UmlInterfaceRealization) umlElement).getTarget());
            son.addInterface(father);
        }
    }

    private void attributeInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_ATTRIBUTE) {
            MyAttribute myAttribute = new MyAttribute(umlElement.getId(), umlElement.getName());
            myAttribute.addType(((UmlAttribute) umlElement).getType());
            if (idClassMap.containsKey(umlElement.getParentId())) {
                MyClass myClass = idClassMap.get(umlElement.getParentId());
                myClass.addAttribute(myAttribute);
            } else if (idInterfaceMap.containsKey(umlElement.getParentId())) {
                MyInterface myInterface = idInterfaceMap.get(umlElement.getParentId());
                myInterface.addAttribute(myAttribute);
            }
        }
    }

    public void operationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_OPERATION) {
            MyClass myClass = idClassMap.get(umlElement.getParentId());
            MyOperation myOperation = new MyOperation(umlElement.getId(),
                    umlElement.getName());
            myOperation.addVisibility(((UmlOperation) umlElement).getVisibility());
            idOperationMap.put(umlElement.getId(), myOperation);
            myClass.addOperation(myOperation);
        }
    }

    public void parameterInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_PARAMETER) {
            MyOperation myOperation = idOperationMap.get(umlElement.getParentId());
            myOperation.addNewParameter(((UmlParameter) umlElement).getDirection(),
                    ((UmlParameter) umlElement).getType());
        }
    }

    @Override
    public int getClassCount() {
        return idClassMap.size();
    }

    @Override
    public int getClassSubClassCount(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        if (!nameClassMap.containsKey(s)) {
            throw new ClassNotFoundException(s);
        } else if (duplicatedClass.contains(s)) {
            throw new ClassDuplicatedException(s);
        } else {
            MyClass myClass = nameClassMap.get(s);
            return myClass.getClassSubClassCount();
        }
    }

    @Override
    public int getClassOperationCount(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        if (!nameClassMap.containsKey(s)) {
            throw new ClassNotFoundException(s);
        } else if (duplicatedClass.contains(s)) {
            throw new ClassDuplicatedException(s);
        } else {
            MyClass myClass = nameClassMap.get(s);
            return myClass.getClassOperationCount();
        }
    }

    @Override
    public Map<Visibility, Integer> getClassOperationVisibility(String s, String s1) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!nameClassMap.containsKey(s)) {
            throw new ClassNotFoundException(s);
        } else if (duplicatedClass.contains(s)) {
            throw new ClassDuplicatedException(s);
        } else {
            MyClass myClass = nameClassMap.get(s);
            return myClass.getClassOperationVisibility(s1);
        }
    }

    @Override
    public List<Integer> getClassOperationCouplingDegree(String s, String s1) throws
            ClassNotFoundException, ClassDuplicatedException,
            MethodWrongTypeException, MethodDuplicatedException {
        if (!nameClassMap.containsKey(s)) {
            throw new ClassNotFoundException(s);
        } else if (duplicatedClass.contains(s)) {
            throw new ClassDuplicatedException(s);
        } else {
            MyClass myClass = nameClassMap.get(s);
            if (myClass.hasWrongType(s1)) {
                throw new MethodWrongTypeException(s, s1);
            } else if (myClass.hasDuplicatedMethod(s1)) {
                throw new MethodDuplicatedException(s, s1);
            } else {
                return myClass.getOperationCouplingDegree(s1);
            }
        }
    }

    @Override
    public int getClassAttributeCouplingDegree(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        if (!nameClassMap.containsKey(s)) {
            throw new ClassNotFoundException(s);
        } else if (duplicatedClass.contains(s)) {
            throw new ClassDuplicatedException(s);
        } else {
            MyClass myClass = nameClassMap.get(s);
            return myClass.getAttributeCouplingDegreePublic(myClass.getId());
        }
    }

    @Override
    public List<String> getClassImplementInterfaceList(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        if (!nameClassMap.containsKey(s)) {
            throw new ClassNotFoundException(s);
        } else if (duplicatedClass.contains(s)) {
            throw new ClassDuplicatedException(s);
        } else {
            MyClass myClass = nameClassMap.get(s);
            ArrayList<String> ansList = new ArrayList<>(myClass.getImplementInterfaceList());
            return ansList.stream()
                    .distinct().collect(Collectors.toCollection(ArrayList::new));
        }
    }

    @Override
    public int getClassDepthOfInheritance(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        if (!nameClassMap.containsKey(s)) {
            throw new ClassNotFoundException(s);
        } else if (duplicatedClass.contains(s)) {
            throw new ClassDuplicatedException(s);
        } else {
            return nameClassMap.get(s).outerGetClassDepthOfInheritance();
        }
    }
}
