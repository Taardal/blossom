package no.taardal.pixelcave.statemachine;

import no.taardal.pixelcave.state.State;

public interface StateChangeListener<T extends State> {

    T getCurrentState();
    void onChangeState(T state);
    void onPushState(T state);
    void onPopState();

}
