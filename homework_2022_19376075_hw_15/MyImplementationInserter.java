import com.oocourse.uml3.interact.exceptions.user.UmlRule003Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule004Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule006Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule009Exception;
import com.oocourse.uml3.models.common.ElementType;
import com.oocourse.uml3.models.elements.UmlElement;
import com.oocourse.uml3.models.elements.UmlGeneralization;
import com.oocourse.uml3.models.elements.UmlInterfaceRealization;
import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlOperation;
import com.oocourse.uml3.models.elements.UmlParameter;
import com.oocourse.uml3.models.elements.UmlTransition;
import com.oocourse.uml3.models.elements.UmlLifeline;
import com.oocourse.uml3.models.elements.UmlMessage;
import com.oocourse.uml3.models.elements.UmlAssociation;
import com.oocourse.uml3.models.elements.UmlAssociationEnd;
import com.oocourse.uml3.models.elements.UmlClassOrInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

public class MyImplementationInserter {
    public HashMap<String, MyClass> getIdClassMap() {
        return idClassMap;
    }

    public HashMap<String, MyClass> getNameClassMap() {
        return nameClassMap;
    }

    public ArrayList<String> getDuplicatedClass() {
        return duplicatedClass;
    }

    public HashMap<String, MyStateMachine> getNameStateMachineMap() {
        return nameStateMachineMap;
    }

    public ArrayList<String> getDuplicatedStateMachine() {
        return duplicatedStateMachine;
    }

    public HashMap<String, MyInteraction> getNameInteractionMap() {
        return nameInteractionMap;
    }

    public ArrayList<String> getDuplicatedInteraction() {
        return duplicatedInteraction;
    }

    public HashMap<String, MyInterface> getIdInterfaceMap() {
        return idInterfaceMap;
    }

    public HashMap<String, MyRegion> getIdRegionMap() {
        return idRegionMap;
    }

    public boolean isCheck007() {
        return check007;
    }

    private final HashMap<String, MyClass> idClassMap;
    private final HashMap<String, MyClass> nameClassMap;
    private final HashMap<String, MyOperation> idOperationMap;
    private final HashMap<String, MyAttribute> idAttributeMap;
    private final HashMap<String, MyParameter> idParameterMap;
    private final HashMap<String, MyAssociation> idAssociationMap;
    private final HashMap<String, MyAssociationEnd> idAssociationEndMap;
    private final HashMap<String, MyInterface> idInterfaceMap;
    private final HashMap<String, MyInterfaceRealization> idInterfaceRealizationMap;
    private final HashMap<String, MyGeneralization> idGeneralizationMap;
    private final ArrayList<String> duplicatedClass;
    private final HashMap<String, MyGenerateBody> idGenerateBodyMap;
    private final HashMap<String, String> circularGeneralizationMap;
    private final HashMap<String, String> duplicatedGeneralizationMap;
    private HashMap<String, String> nowVisited;
    private HashMap<String, String> allVisited;
    private boolean alreadyCheckGeneralization;
    private final HashMap<String, MyStateMachine> idStateMachineMap;
    private final HashMap<String, MyStateMachine> nameStateMachineMap;
    private final HashMap<String, MyRegion> idRegionMap;
    private final HashMap<String, MyTransition> idTranslationMap;
    private final ArrayList<String> duplicatedStateMachine;
    private final HashMap<String, MyCollaboration> idCollaborationMap;
    private final HashMap<String, MyInteraction> idInteractionMap;
    private final HashMap<String, MyInteraction> nameInteractionMap;
    private final ArrayList<String> duplicatedInteraction;
    private final UmlElement[] elements;
    private boolean check007;

    public MyImplementationInserter(UmlElement... umlElements) {
        elements = umlElements;
        idOperationMap = new HashMap<>();
        idClassMap = new HashMap<>();
        idInterfaceMap = new HashMap<>();
        nameClassMap = new HashMap<>();
        idAttributeMap = new HashMap<>();
        idAssociationEndMap = new HashMap<>();
        duplicatedClass = new ArrayList<>();
        idGeneralizationMap = new HashMap<>();
        idInterfaceRealizationMap = new HashMap<>();
        idParameterMap = new HashMap<>();
        idAssociationMap = new HashMap<>();
        idGenerateBodyMap = new HashMap<>();
        circularGeneralizationMap = new HashMap<>();
        duplicatedGeneralizationMap = new HashMap<>();
        alreadyCheckGeneralization = false;
        idStateMachineMap = new HashMap<>();
        nameStateMachineMap = new HashMap<>();
        idRegionMap = new HashMap<>();
        idTranslationMap = new HashMap<>();
        duplicatedStateMachine = new ArrayList<>();
        idCollaborationMap = new HashMap<>();
        idInteractionMap = new HashMap<>();
        nameInteractionMap = new HashMap<>();
        duplicatedInteraction = new ArrayList<>();
        check007 = true;
    }

    public boolean checkForUml001() {
        for (MyClass myClass : idClassMap.values()) {
            if (!myClass.checkName()) {
                return false; }
        }
        for (MyOperation myOperation : idOperationMap.values()) {
            if (!myOperation.checkName()) {
                return false; }
        }
        for (MyAttribute myAttribute : idAttributeMap.values()) {
            if (!myAttribute.checkName() && !idCollaborationMap
                    .containsKey(myAttribute.getParentId())) {
                return false; }
        }
        for (MyParameter myParameter : idParameterMap.values()) {
            if (!myParameter.checkName()) {
                return false; }
        }
        for (MyInterface myInterface : idInterfaceMap.values()) {
            if (!myInterface.checkName()) {
                return false; }
        }
        return true;
    }

    private int searchGeneralization0(MyGenerateBody myGenerateBody, String oriID) {
        if (myGenerateBody.getFathers().isEmpty()) {
            return 2;
        }
        for (MyGenerateBody fatherBody : myGenerateBody.getFathers().values()) {
            if (Objects.equals(oriID, fatherBody.getId())) { return 0; }
        }
        for (MyGenerateBody fatherBody : myGenerateBody.getFathers().values()) {
            if (!nowVisited.containsKey(fatherBody.getId())) {
                nowVisited.put(fatherBody.getId(), fatherBody.getName());
                int re = searchGeneralization0(fatherBody, oriID);
                if (re != 2) { return re; }
                nowVisited.remove(fatherBody.getId());
            }
        }
        return 2;
    }

    private int searchGeneralization1(MyGenerateBody myGenerateBody) {
        if (myGenerateBody.isSameFather()) { return 1; }
        if (myGenerateBody.getFathers().isEmpty()) {
            allVisited.putAll(nowVisited);
            return 2;
        }
        for (MyGenerateBody fatherBody : myGenerateBody.getFathers().values()) {
            if (allVisited.containsKey(fatherBody.getId())) { return 1; }
            if (nowVisited.containsKey(fatherBody.getId())) { return 1; }
            nowVisited.put(fatherBody.getId(), fatherBody.getName());
            int re = searchGeneralization1(fatherBody);
            if (re != 2) { return re; }
            nowVisited.remove(fatherBody.getId());
        }
        return 2;
    }

    private void generalizationCheckPool() {
        if (!alreadyCheckGeneralization) {
            for (MyGenerateBody gb : idGenerateBodyMap.values()) {
                nowVisited = new HashMap<>();
                allVisited = new HashMap<>();
                int re = searchGeneralization0(gb, gb.getId());
                if (re == 0) {
                    circularGeneralizationMap.put(gb.getId(), gb.getName()); }
                nowVisited = new HashMap<>();
                allVisited = new HashMap<>();
                re = searchGeneralization1(gb);
                if (re == 1) {
                    duplicatedGeneralizationMap.put(gb.getId(), gb.getName()); }
            }
            alreadyCheckGeneralization = true;
        }
    }

    public void checkForUml003() throws UmlRule003Exception {
        generalizationCheckPool();
        Set<UmlClassOrInterface> set = new HashSet<>();
        for (UmlElement element : elements) {
            if (circularGeneralizationMap.containsKey(element.getId())) {
                set.add((UmlClassOrInterface) element);
            }
        }
        if (!set.isEmpty()) { throw new UmlRule003Exception(set); }
    }

    public void checkForUml004() throws UmlRule004Exception {
        generalizationCheckPool();
        Set<UmlClassOrInterface> set = new HashSet<>();
        for (UmlElement element : elements) {
            if (duplicatedGeneralizationMap.containsKey(element.getId())) {
                set.add((UmlClassOrInterface) element);
            }
        }
        if (!set.isEmpty()) {
            throw new UmlRule004Exception(set);
        }
    }

    public void checkForUml006() throws UmlRule006Exception {
        for (MyInteraction myInteraction : idInteractionMap.values()) {
            for (MyLifeline myLifeline : myInteraction.getLifeLines().values()) {
                if ((myLifeline.getAttribute() == null)
                        || !Objects.equals(myInteraction.getCollaboration()
                        .getId(), myLifeline.getAttribute().getParentId())) {
                    throw new UmlRule006Exception();
                }
            }
        }
    }

    public void checkForUml009() throws UmlRule009Exception {
        for (MyRegion myRegion : idRegionMap.values()) {
            if (!myRegion.checkStataGuard()) { throw new UmlRule009Exception(); } }
    }

    public void classInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_CLASS) {
            MyClass myClass = new MyClass(umlElement.getId(), umlElement.getName());
            idClassMap.put(umlElement.getId(), myClass);
            MyGenerateBody myGenerateBody = new MyGenerateBody(umlElement
                    .getId(), umlElement.getName());
            idGenerateBodyMap.put(myGenerateBody.getId(), myGenerateBody);
            if (nameClassMap.containsKey(umlElement.getName())) {
                duplicatedClass.add(umlElement.getName());
            } else {
                nameClassMap.put(umlElement.getName(), myClass);
            }
        }
    }

    public void interfaceInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_INTERFACE) {
            MyInterface myInterface = new MyInterface(umlElement.getId(), umlElement.getName());
            idInterfaceMap.put(umlElement.getId(), myInterface);
            MyGenerateBody myGenerateBody = new MyGenerateBody(umlElement
                    .getId(), umlElement.getName());
            idGenerateBodyMap.put(myGenerateBody.getId(), myGenerateBody);
        }
    }

    public void generalizationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_GENERALIZATION) {
            MyGeneralization myGeneralization = new MyGeneralization(umlElement
                    .getId(), umlElement.getName());
            myGeneralization.addSource(((UmlGeneralization) umlElement).getSource());
            myGeneralization.addTarget(((UmlGeneralization) umlElement).getTarget());
            idGeneralizationMap.put(myGeneralization.getId(), myGeneralization);
            MyGenerateBody myGenerateBody1 = idGenerateBodyMap
                    .get(((UmlGeneralization) umlElement).getSource());
            MyGenerateBody myGenerateBody2 = idGenerateBodyMap
                    .get(((UmlGeneralization) umlElement).getTarget());
            myGenerateBody1.addFathers(myGenerateBody2);
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

    public void interfaceRealizationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_INTERFACE_REALIZATION) {
            MyInterfaceRealization myInterfaceRealization = new MyInterfaceRealization(umlElement
                    .getId(), umlElement.getName());
            MyClass son = idClassMap.get(((UmlInterfaceRealization) umlElement).getSource());
            MyInterface father =
                    idInterfaceMap.get(((UmlInterfaceRealization) umlElement).getTarget());
            son.addInterface(father);
            idInterfaceRealizationMap.put(myInterfaceRealization.getId(), myInterfaceRealization);
        }
    }

    public void attributeInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_ATTRIBUTE) {
            MyAttribute myAttribute = new MyAttribute(umlElement.getId()
                    , umlElement.getName(), umlElement.getParentId());
            myAttribute.addType(((UmlAttribute) umlElement).getType());
            myAttribute.addVisibility(((UmlAttribute) umlElement).getVisibility());
            if (idClassMap.containsKey(umlElement.getParentId())) {
                MyClass myClass = idClassMap.get(umlElement.getParentId());
                myClass.addAttribute(myAttribute);
            } else if (idInterfaceMap.containsKey(umlElement.getParentId())) {
                MyInterface myInterface = idInterfaceMap.get(umlElement.getParentId());
                myInterface.addAttribute(myAttribute);
            } else if (idCollaborationMap.containsKey(umlElement.getParentId())) {
                MyCollaboration myCollaboration = idCollaborationMap.get(umlElement.getParentId());
                myCollaboration.addAttribute(myAttribute);
            }
            idAttributeMap.put(umlElement.getId(), myAttribute);
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

    public void associationEndInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_ASSOCIATION_END) {
            MyAssociationEnd myAssociationEnd = new MyAssociationEnd(umlElement.getId(),
                    umlElement.getName());
            if (idClassMap.containsKey(((UmlAssociationEnd)umlElement).getReference())) {
                MyClass myClass = idClassMap.get(((UmlAssociationEnd)umlElement).getReference());
                myClass.addAssociationsEnd(myAssociationEnd);
            } else if (idInterfaceMap.containsKey(((UmlAssociationEnd)umlElement).getReference())) {
                MyInterface myInterface = idInterfaceMap
                        .get(((UmlAssociationEnd)umlElement).getReference());
                myInterface.addAssociationsEnd(myAssociationEnd);
            }
            idAssociationEndMap.put(umlElement.getId(), myAssociationEnd);
        }
    }

    public void parameterInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_PARAMETER) {
            MyOperation myOperation = idOperationMap.get(umlElement.getParentId());
            MyParameter myParameter = new MyParameter(umlElement.getId(),
                    umlElement.getName());
            myParameter.addDirection(((UmlParameter) umlElement).getDirection());
            myParameter.addType(((UmlParameter) umlElement).getType());
            myOperation.addNewParameter(myParameter);
            idParameterMap.put(myParameter.getId(), myParameter);
        }
    }

    public void associationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_ASSOCIATION) {
            MyAssociation myAssociation = new MyAssociation(umlElement.getId(),
                    umlElement.getName());
            if (umlElement instanceof UmlAssociation) {
                MyAssociationEnd myAssociationEnd1 = idAssociationEndMap
                        .get(((UmlAssociation) umlElement).getEnd1());
                MyAssociationEnd myAssociationEnd2 = idAssociationEndMap
                        .get(((UmlAssociation) umlElement).getEnd2());
                myAssociation.addEnd1(myAssociationEnd1);
                myAssociation.addEnd2(myAssociationEnd2);
                idAssociationMap.put(umlElement.getId(), myAssociation);
            }
        }
    }

    public void stateMachineInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_STATE_MACHINE) {
            MyStateMachine myStateMachine = new MyStateMachine(umlElement.getId()
                    , umlElement.getName());
            idStateMachineMap.put(umlElement.getId(), myStateMachine);
            if (nameStateMachineMap.containsKey(umlElement.getName())) {
                duplicatedStateMachine.add(umlElement.getName());
            } else { nameStateMachineMap.put(umlElement.getName(), myStateMachine); }
        }
    }

    public void regionInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_REGION) {
            MyStateMachine myStateMachine = idStateMachineMap.get(umlElement.getParentId());
            MyRegion myRegion = new MyRegion(umlElement.getId()
                    , umlElement.getName());
            idRegionMap.put(umlElement.getId(), myRegion);
            myStateMachine.addRegion(myRegion);
        }
    }

    public void pseudoStateInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_PSEUDOSTATE) {
            MyRegion myRegion = idRegionMap.get(umlElement.getParentId());
            MyState myState = new MyState(umlElement.getId(),
                    umlElement.getName());
            myRegion.addPseudoState(myState); }
    }

    public void stateInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_STATE) {
            MyRegion myRegion = idRegionMap.get(umlElement.getParentId());
            MyState myState = new MyState(umlElement.getId(),
                    umlElement.getName());
            myRegion.addState(myState); }
    }

    public void finalStateInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_FINAL_STATE) {
            MyRegion myRegion = idRegionMap.get(umlElement.getParentId());
            MyState myState = new MyState(umlElement.getId(),
                    umlElement.getName());
            myRegion.addFinialState(myState); }
    }

    public void translationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_TRANSITION) {
            MyRegion myRegion = idRegionMap.get(umlElement.getParentId());
            MyTransition myTransition = new MyTransition(umlElement.getId(), umlElement.getName()
                    , ((UmlTransition) umlElement).getSource()
                    , ((UmlTransition) umlElement).getTarget()
                    , ((UmlTransition) umlElement).getGuard());
            idTranslationMap.put(umlElement.getId(), myTransition);
            myRegion.addTransition(myTransition); }
    }

    public void eventInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_EVENT) {
            MyTransition myTransition = idTranslationMap.get(umlElement.getParentId());
            MyEvent myEvent = new MyEvent(umlElement.getId(), umlElement.getName());
            myTransition.addEvent(myEvent); }
    }

    public void opaqueBehaviorInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_OPAQUE_BEHAVIOR) {
            MyTransition myTransition = idTranslationMap.get(umlElement.getParentId());
            MyOpaqueBehavior myOpaqueBehavior = new MyOpaqueBehavior(umlElement.getId()
                    , umlElement.getName());
            myTransition.addOpaqueBehavior(myOpaqueBehavior); }
    }

    public void collaborationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_COLLABORATION) {
            MyCollaboration myCollaboration = new MyCollaboration(umlElement.getId()
                    , umlElement.getName());
            idCollaborationMap.put(umlElement.getId(), myCollaboration); }
    }

    public void interactionInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_INTERACTION) {
            MyCollaboration myCollaboration = idCollaborationMap.get(umlElement.getParentId());
            MyInteraction myInteraction = new MyInteraction(umlElement.getId()
                    , umlElement.getName());
            idInteractionMap.put(umlElement.getId(), myInteraction);
            if (nameInteractionMap.containsKey(umlElement.getName())) {
                duplicatedInteraction.add(umlElement.getName());
            } else { nameInteractionMap.put(umlElement.getName(), myInteraction); }
            myCollaboration.addInteraction(myInteraction); }
    }

    public void lifelineInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_LIFELINE) {
            MyInteraction myInteraction = idInteractionMap.get(umlElement.getParentId());
            MyLifeline myLifeline = new MyLifeline(umlElement.getId()
                    , umlElement.getName());
            myLifeline.addAttribute(idAttributeMap.get(((UmlLifeline) umlElement).getRepresent()));
            myInteraction.addLifeLine(myLifeline); }
    }

    public void endPointInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_ENDPOINT) {
            MyInteraction myInteraction = idInteractionMap.get(umlElement.getParentId());
            MyEndPoint myEndPoint = new MyEndPoint(umlElement.getId()
                    , umlElement.getName());
            myInteraction.addEndPoint(myEndPoint); }
    }

    public void messageInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_MESSAGE) {
            MyInteraction myInteraction = idInteractionMap.get(umlElement.getParentId());
            MyMessage myMessage = new MyMessage(umlElement.getId()
                    , umlElement.getName(), ((UmlMessage) umlElement).getMessageSort());
            myMessage.addSourceTargetId(((UmlMessage) umlElement).getSource()
                    , ((UmlMessage) umlElement).getTarget());
            check007 = check007 && myInteraction.addMessage(myMessage); }
    }
}
