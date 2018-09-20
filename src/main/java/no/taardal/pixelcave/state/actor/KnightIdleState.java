package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.animation.AnimationType;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.statemachine.ActorStateListener;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightIdleState extends MovementState<Knight> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightIdleState.class);

    public KnightIdleState(Knight actor, ActorStateListener actorStateListener) {
        super(actor, actorStateListener);
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get(AnimationType.IDLE);
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        actor.setVelocity(new Vector2f(0, 0));
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            actorStateListener.onChangeState(new KnightRunningState(actor, actorStateListener));
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT) && actor.getVelocity().getY() == 0) {
            actorStateListener.onChangeState(new KnightJumpingState(actor, actorStateListener));
        }
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        if (isStandingOnSolidTile(world)) {
            getAnimation().update();
        } else {
            actorStateListener.onChangeState(new KnightFallingState(actor, actorStateListener));
        }
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }
}
