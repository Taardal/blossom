package no.taardal.pixelcave.state;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.domain.Vector2f;
import no.taardal.pixelcave.domain.World;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.domain.AnimationType;
import no.taardal.pixelcave.domain.GameActor;
import no.taardal.pixelcave.statemachine.StateChangeListener;

public class KnightIdleState extends MovementState implements GameActorState {

    public KnightIdleState(GameActor gameActor, StateChangeListener stateChangeListener) {
        super(gameActor, stateChangeListener);
    }

    @Override
    public Animation getAnimation() {
        return gameActor.getAnimations().get(AnimationType.IDLE);
    }

    @Override
    public void onEntry() {
        gameActor.setVelocity(Vector2f.zero());
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        getAnimation().update();
    }

    @Override
    public void draw(Camera camera) {
        getAnimation().draw(gameActor, camera);
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

}
