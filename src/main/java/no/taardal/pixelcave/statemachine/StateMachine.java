package no.taardal.pixelcave.statemachine;

import no.taardal.pixelcave.listener.StateListener;
import no.taardal.pixelcave.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;

public class StateMachine<T extends State> implements StateListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateMachine.class);

    private Deque<T> stateDeque;

    public StateMachine() {
        stateDeque = new ArrayDeque<>();
    }

    @Override
    public T getCurrentState() {
        return !stateDeque.isEmpty() ? stateDeque.getFirst() : null;
    }

    @Override
    public void onChangeState(T state) {
        if (!stateDeque.isEmpty()) {
            onPopState();
        }
        onPushState(state);
    }

    @Override
    public void onPushState(T state) {
        state.onEntry();
        stateDeque.addFirst(state);
    }

    @Override
    public void onPopState() {
        stateDeque.getFirst().onExit();
        stateDeque.removeFirst();
    }

}
