package no.taardal.pixelcave.statemachine;

import no.taardal.pixelcave.state.actor.ActorState;

public interface ActorStateListener {

    void onChangeState(ActorState actorState);

    void onPushState(ActorState actorState);

    void onPopState();

}
