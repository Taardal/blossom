package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.animation.AnimationType;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.statemachine.ActorStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightJumpingState extends KnightFallingState {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightJumpingState.class);
    private static final int MAX_JUMPING_VELOCITY_Y = 100;
    private static final int JUMPING_VELOCITY_Y = -300;

    public KnightJumpingState(Knight actor, ActorStateListener actorStateListener) {
        super(actor, actorStateListener);
    }

    @Override
    public Animation getAnimation() {
        if (actor.getVelocity().getY() != 0 && actor.getVelocity().getY() < MAX_JUMPING_VELOCITY_Y) {
            return actor.getAnimations().get(AnimationType.JUMP);
        } else {
            return super.getAnimation();
        }
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        actor.getVelocity().setY(JUMPING_VELOCITY_Y);
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        super.handleInput(keyboard);
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT) && actor.getVelocity().getY() == 0) {
            actor.setVelocity(actor.getVelocity().withY(JUMPING_VELOCITY_Y));
        }
    }

    @Override
    public void onExit() {
        super.onExit();
        actor.getAnimations().get(AnimationType.JUMP).reset();
    }

}
