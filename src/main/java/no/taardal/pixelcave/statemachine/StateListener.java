package no.taardal.pixelcave.statemachine;

import no.taardal.pixelcave.state.State;

public interface StateListener<T extends State> {

    T getCurrentState();
    void onChangeState(T state);
    void onPushState(T state);
    void onPopState();

}
