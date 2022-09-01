import com.oocourse.uml2.interact.common.Pair;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.MethodWrongTypeException;
import com.oocourse.uml2.interact.exceptions.user.MethodDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.TransitionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNeverCreatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineCreatedRepeatedlyException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.format.UserApi;
import com.oocourse.uml2.models.common.ElementType;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlLifeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MyImplementation implements UserApi {
    private final UmlElement[] umlElements;
    private final MyImplementationInserter myImplementationInserter;
    private HashMap<String, MyClass> idClassMap;
    private HashMap<String, MyClass> nameClassMap;
    private ArrayList<String> duplicatedClass;

    private HashMap<String, MyStateMachine> nameStateMachineMap;
    private ArrayList<String> duplicatedStateMachine;

    private HashMap<String, MyInteraction> nameInteractionMap;
    private ArrayList<String> duplicatedInteraction;

    public MyImplementation(UmlElement... elements) {
        umlElements = elements;
        myImplementationInserter = new MyImplementationInserter();
        for (UmlElement umlElement : elements) {
            myImplementationInserter.classInsert(umlElement);
            myImplementationInserter.interfaceInsert(umlElement);
        }
        receive();
        for (UmlElement umlElement : elements) {
            myImplementationInserter.generalizationInsert(umlElement);
            myImplementationInserter.interfaceRealizationInsert(umlElement);
            myImplementationInserter.attributeInsert(umlElement);
            myImplementationInserter.operationInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.parameterInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.stateMachineInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.regionInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.pseudoStateInsert(umlElement);
            myImplementationInserter.stateInsert(umlElement);
            myImplementationInserter.finalStateInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.translationInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.eventInsert(umlElement);
            myImplementationInserter.opaqueBehaviorInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.collaborationInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.interactionInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.lifelineInsert(umlElement);
            myImplementationInserter.endPointInsert(umlElement);
        }
        for (UmlElement umlElement : elements) {
            myImplementationInserter.messageInsert(umlElement);
        }
    }

    private void receive()
    {
        idClassMap = myImplementationInserter.getIdClassMap();
        nameClassMap = myImplementationInserter.getNameClassMap();
        duplicatedClass = myImplementationInserter.getDuplicatedClass();
        nameStateMachineMap = myImplementationInserter.getNameStateMachineMap();
        duplicatedStateMachine = myImplementationInserter.getDuplicatedStateMachine();
        nameInteractionMap = myImplementationInserter.getNameInteractionMap();
        duplicatedInteraction = myImplementationInserter.getDuplicatedInteraction();
    }

    private void classExceptionFinder(String s) throws ClassNotFoundException
            , ClassDuplicatedException {
        if (!nameClassMap.containsKey(s)) {
            throw new ClassNotFoundException(s);
        } else if (duplicatedClass.contains(s)) {
            throw new ClassDuplicatedException(s);
        }
    }

    @Override
    public int getClassCount() { return idClassMap.size(); }

    @Override
    public int getClassSubClassCount(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        classExceptionFinder(s);
        MyClass myClass = nameClassMap.get(s);
        return myClass.getClassSubClassCount();

    }

    @Override
    public int getClassOperationCount(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        classExceptionFinder(s);
        MyClass myClass = nameClassMap.get(s);
        return myClass.getClassOperationCount();
    }

    @Override
    public Map<Visibility, Integer> getClassOperationVisibility(String s, String s1) throws
            ClassNotFoundException, ClassDuplicatedException {
        classExceptionFinder(s);
        MyClass myClass = nameClassMap.get(s);
        return myClass.getClassOperationVisibility(s1);
    }

    @Override
    public List<Integer> getClassOperationCouplingDegree(String s, String s1) throws
            ClassNotFoundException, ClassDuplicatedException,
            MethodWrongTypeException, MethodDuplicatedException {
        classExceptionFinder(s);
        MyClass myClass = nameClassMap.get(s);
        if (myClass.hasWrongType(s1)) {
            throw new MethodWrongTypeException(s, s1);
        } else if (myClass.hasDuplicatedMethod(s1)) {
            throw new MethodDuplicatedException(s, s1);
        } else {
            return myClass.getOperationCouplingDegree(s1);
        }
    }

    @Override
    public int getClassAttributeCouplingDegree(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        classExceptionFinder(s);
        MyClass myClass = nameClassMap.get(s);
        return myClass.getAttributeCouplingDegreePublic(myClass.getId());
    }

    @Override
    public List<String> getClassImplementInterfaceList(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        classExceptionFinder(s);
        MyClass myClass = nameClassMap.get(s);
        ArrayList<String> ansList = new ArrayList<>(myClass.getImplementInterfaceList());
        return ansList.stream()
                .distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public int getClassDepthOfInheritance(String s) throws ClassNotFoundException,
            ClassDuplicatedException {
        classExceptionFinder(s);
        return nameClassMap.get(s).outerGetClassDepthOfInheritance();
    }

    private void stateMachineExceptionFinder(String s) throws StateMachineNotFoundException
            , StateMachineDuplicatedException {
        if (!nameStateMachineMap.containsKey(s)) {
            throw new StateMachineNotFoundException(s);
        } else if (duplicatedStateMachine.contains(s)) {
            throw new StateMachineDuplicatedException(s);
        }
    }

    private void stateExceptionFinder(MyRegion myRegion, String s
            , String s1) throws StateNotFoundException, StateDuplicatedException {
        if (!myRegion.checkStateExit(s1)) {
            throw new StateNotFoundException(s, s1);
        } else if (myRegion.checkStateDuplicated(s1)) {
            throw new StateDuplicatedException(s, s1);
        }
    }

    @Override
    public int getStateCount(String s) throws StateMachineNotFoundException
            , StateMachineDuplicatedException {
        stateMachineExceptionFinder(s);
        MyStateMachine myStateMachine = nameStateMachineMap.get(s);
        return myStateMachine.getStatesCount();
    }

    @Override
    public boolean getStateIsCriticalPoint(String s, String s1) throws StateMachineNotFoundException
            , StateMachineDuplicatedException, StateNotFoundException, StateDuplicatedException {
        stateMachineExceptionFinder(s);
        MyStateMachine myStateMachine = nameStateMachineMap.get(s);
        MyRegion myRegion = myStateMachine.getRegion();
        stateExceptionFinder(myRegion, s, s1);
        return myRegion.checkCriticalState(s1);
    }

    @Override
    public List<String> getTransitionTrigger(String s, String s1
            , String s2) throws StateMachineNotFoundException, StateMachineDuplicatedException
            , StateNotFoundException, StateDuplicatedException, TransitionNotFoundException {
        stateMachineExceptionFinder(s);
        MyStateMachine myStateMachine = nameStateMachineMap.get(s);
        MyRegion myRegion = myStateMachine.getRegion();
        stateExceptionFinder(myRegion, s, s1);
        stateExceptionFinder(myRegion, s, s2);
        if (!myRegion.checkTransition(s1, s2)) {
            throw new TransitionNotFoundException(s, s1, s2);
        } else {
            return myRegion.getEvents(s1, s2);
        }
    }

    private void interactionExceptionFinder(String s) throws InteractionNotFoundException
            , InteractionDuplicatedException {
        if (!nameInteractionMap.containsKey(s)) {
            throw new InteractionNotFoundException(s);
        } else if (duplicatedInteraction.contains(s)) {
            throw new InteractionDuplicatedException(s);
        }
    }

    private void lifeLineExceptionFinder(MyInteraction myInteraction
            , String s, String s1) throws LifelineNotFoundException, LifelineDuplicatedException {
        if (!myInteraction.checkLifeLineExit(s1)) {
            throw new LifelineNotFoundException(s, s1);
        } else if (myInteraction.checkLifeLineDuplicated(s1)) {
            throw new LifelineDuplicatedException(s, s1);
        }
    }

    @Override
    public int getParticipantCount(String s) throws InteractionNotFoundException
            , InteractionDuplicatedException {
        interactionExceptionFinder(s);
        MyInteraction myInteraction = nameInteractionMap.get(s);
        return myInteraction.getParticipantCount();
    }

    @Override
    public UmlLifeline getParticipantCreator(String s
            , String s1) throws InteractionNotFoundException, InteractionDuplicatedException
            , LifelineNotFoundException, LifelineDuplicatedException
            , LifelineNeverCreatedException, LifelineCreatedRepeatedlyException {
        interactionExceptionFinder(s);
        MyInteraction myInteraction = nameInteractionMap.get(s);
        lifeLineExceptionFinder(myInteraction, s, s1);
        MyLifeline myLifeline = myInteraction.getLifeLine(s1);
        if (!myLifeline.checkCreatExit()) {
            throw new LifelineNeverCreatedException(s, s1);
        } else if (myLifeline.checkCreatDuplicated()) {
            throw new LifelineCreatedRepeatedlyException(s, s1);
        } else {
            String creatorId = myLifeline.findCreatorId();
            for (UmlElement umlElement : umlElements) {
                if (Objects.equals(umlElement.getId(), creatorId) && (umlElement
                        .getElementType() == ElementType.UML_LIFELINE)) {
                    return (UmlLifeline) umlElement;
                }
            }
            return null;
        }
    }

    @Override
    public Pair<Integer, Integer> getParticipantLostAndFound(String s
            , String s1) throws InteractionNotFoundException, InteractionDuplicatedException
            , LifelineNotFoundException, LifelineDuplicatedException {
        interactionExceptionFinder(s);
        MyInteraction myInteraction = nameInteractionMap.get(s);
        lifeLineExceptionFinder(myInteraction, s, s1);
        MyLifeline myLifeline = myInteraction.getLifeLine(s1);
        int foundCount = myLifeline.receiveFoundCount();
        int lostCount = myLifeline.sendLostCount();
        return new Pair<>(foundCount, lostCount);
    }
}