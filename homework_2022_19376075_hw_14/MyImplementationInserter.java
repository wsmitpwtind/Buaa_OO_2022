import com.oocourse.uml2.models.common.ElementType;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlParameter;
import com.oocourse.uml2.models.elements.UmlTransition;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;

import java.util.ArrayList;
import java.util.HashMap;

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

    private final HashMap<String, MyClass> idClassMap;
    private final HashMap<String, MyClass> nameClassMap;
    private final HashMap<String, MyInterface> idInterfaceMap;
    private final HashMap<String, MyOperation> idOperationMap;
    private final HashMap<String, MyAttribute> idAttributeMap;
    private final ArrayList<String> duplicatedClass;

    private final HashMap<String, MyStateMachine> idStateMachineMap;
    private final HashMap<String, MyStateMachine> nameStateMachineMap;
    private final HashMap<String, MyRegion> idRegionMap;
    private final HashMap<String, MyTransition> idTranslationMap;
    private final ArrayList<String> duplicatedStateMachine;

    private final HashMap<String, MyCollaboration> idCollaborationMap;
    private final HashMap<String, MyInteraction> idInteractionMap;
    private final HashMap<String, MyInteraction> nameInteractionMap;
    private final ArrayList<String> duplicatedInteraction;

    public MyImplementationInserter() {
        idOperationMap = new HashMap<>();
        idClassMap = new HashMap<>();
        idInterfaceMap = new HashMap<>();
        nameClassMap = new HashMap<>();
        idAttributeMap = new HashMap<>();
        duplicatedClass = new ArrayList<>();
        idStateMachineMap = new HashMap<>();
        nameStateMachineMap = new HashMap<>();
        idRegionMap = new HashMap<>();
        idTranslationMap = new HashMap<>();
        duplicatedStateMachine = new ArrayList<>();
        idCollaborationMap = new HashMap<>();
        idInteractionMap = new HashMap<>();
        nameInteractionMap = new HashMap<>();
        duplicatedInteraction = new ArrayList<>();
    }

    public void classInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_CLASS) {
            MyClass myClass = new MyClass(umlElement.getId(), umlElement.getName());
            idClassMap.put(umlElement.getId(), myClass);
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
        }
    }

    public void generalizationInsert(UmlElement umlElement) {
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

    public void interfaceRealizationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_INTERFACE_REALIZATION) {
            MyClass son = idClassMap.get(((UmlInterfaceRealization) umlElement).getSource());
            MyInterface father =
                    idInterfaceMap.get(((UmlInterfaceRealization) umlElement).getTarget());
            son.addInterface(father);
        }
    }

    public void attributeInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_ATTRIBUTE) {
            MyAttribute myAttribute = new MyAttribute(umlElement.getId(), umlElement.getName());
            myAttribute.addType(((UmlAttribute) umlElement).getType());
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

    public void parameterInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_PARAMETER) {
            MyOperation myOperation = idOperationMap.get(umlElement.getParentId());
            myOperation.addNewParameter(((UmlParameter) umlElement).getDirection(),
                    ((UmlParameter) umlElement).getType());
        }
    }

    public void stateMachineInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_STATE_MACHINE) {
            MyStateMachine myStateMachine = new MyStateMachine(umlElement.getId()
                    , umlElement.getName());
            idStateMachineMap.put(umlElement.getId(), myStateMachine);
            if (nameStateMachineMap.containsKey(umlElement.getName())) {
                duplicatedStateMachine.add(umlElement.getName());
            } else {
                nameStateMachineMap.put(umlElement.getName(), myStateMachine);
            }
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
            myRegion.addPseudoState(myState);
        }
    }

    public void stateInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_STATE) {
            MyRegion myRegion = idRegionMap.get(umlElement.getParentId());
            MyState myState = new MyState(umlElement.getId(),
                    umlElement.getName());
            myRegion.addState(myState);
        }
    }

    public void finalStateInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_FINAL_STATE) {
            MyRegion myRegion = idRegionMap.get(umlElement.getParentId());
            MyState myState = new MyState(umlElement.getId(),
                    umlElement.getName());
            myRegion.addFinialState(myState);
        }
    }

    public void translationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_TRANSITION) {
            MyRegion myRegion = idRegionMap.get(umlElement.getParentId());
            MyTransition myTransition = new MyTransition(umlElement.getId(), umlElement.getName()
                    , ((UmlTransition) umlElement).getSource()
                    , ((UmlTransition) umlElement).getTarget());
            idTranslationMap.put(umlElement.getId(), myTransition);
            myRegion.addTransition(myTransition);
        }
    }

    public void eventInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_EVENT) {
            MyTransition myTransition = idTranslationMap.get(umlElement.getParentId());
            MyEvent myEvent = new MyEvent(umlElement.getId(), umlElement.getName());
            myTransition.addEvent(myEvent);
        }
    }

    public void opaqueBehaviorInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_OPAQUE_BEHAVIOR) {
            MyTransition myTransition = idTranslationMap.get(umlElement.getParentId());
            MyOpaqueBehavior myOpaqueBehavior = new MyOpaqueBehavior(umlElement.getId()
                    , umlElement.getName());
            myTransition.addOpaqueBehavior(myOpaqueBehavior);
        }
    }

    public void collaborationInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_COLLABORATION) {
            MyCollaboration myCollaboration = new MyCollaboration(umlElement.getId()
                    , umlElement.getName());
            idCollaborationMap.put(umlElement.getId(), myCollaboration);
        }
    }

    public void interactionInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_INTERACTION) {
            MyCollaboration myCollaboration = idCollaborationMap.get(umlElement.getParentId());
            MyInteraction myInteraction = new MyInteraction(umlElement.getId()
                    , umlElement.getName());
            idInteractionMap.put(umlElement.getId(), myInteraction);
            if (nameInteractionMap.containsKey(umlElement.getName())) {
                duplicatedInteraction.add(umlElement.getName());
            } else {
                nameInteractionMap.put(umlElement.getName(), myInteraction);
            }
            myCollaboration.addInteraction(myInteraction);
        }
    }

    public void lifelineInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_LIFELINE) {
            MyInteraction myInteraction = idInteractionMap.get(umlElement.getParentId());
            MyLifeline myLifeline = new MyLifeline(umlElement.getId()
                    , umlElement.getName());
            myLifeline.addAttribute(idAttributeMap.get(((UmlLifeline) umlElement).getRepresent()));
            myInteraction.addLifeLine(myLifeline);
        }
    }

    public void endPointInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_ENDPOINT) {
            MyInteraction myInteraction = idInteractionMap.get(umlElement.getParentId());
            MyEndPoint myEndPoint = new MyEndPoint(umlElement.getId()
                    , umlElement.getName());
            myInteraction.addEndPoint(myEndPoint);
        }
    }

    public void messageInsert(UmlElement umlElement) {
        if (umlElement.getElementType() == ElementType.UML_MESSAGE) {
            MyInteraction myInteraction = idInteractionMap.get(umlElement.getParentId());
            MyMessage myMessage = new MyMessage(umlElement.getId()
                    , umlElement.getName(), ((UmlMessage) umlElement).getMessageSort());
            myMessage.addSourceTargetId(((UmlMessage) umlElement).getSource()
                    , ((UmlMessage) umlElement).getTarget());
            myInteraction.addMessage(myMessage);
        }
    }
}
