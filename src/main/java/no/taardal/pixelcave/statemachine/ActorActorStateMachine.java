package no.taardal.pixelcave.statemachine;

import no.taardal.pixelcave.state.actor.ActorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;

public class ActorActorStateMachine implements ActorStateListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorActorStateMachine.class);

    private Deque<ActorState> actorStateDeque;

    public ActorActorStateMachine() {
        actorStateDeque = new ArrayDeque<>();
    }

    @Override
    public void onChangeState(ActorState actorState) {
        if (!isEmpty()) {
            onPopState();
        }
        onPushState(actorState);
    }

    @Override
    public void onPopState() {
        actorStateDeque.getFirst().onExit();
        actorStateDeque.removeFirst();
    }

    @Override
    public void onPushState(ActorState actorState) {
        actorState.onEntry();
        actorStateDeque.addFirst(actorState);
    }

    public ActorState getCurrentState() {
        return !isEmpty() ? actorStateDeque.getFirst() : null;
    }

    public boolean isEmpty() {
        return actorStateDeque.isEmpty();
    }

}
