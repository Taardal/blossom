package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.animation.AnimationType;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.statemachine.ActorStateListener;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightFallingState extends MovementState<Knight> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightFallingState.class);
    private static final int TERMINAL_VELOCITY = 300;

    public KnightFallingState(Knight actor, ActorStateListener actorStateListener) {
        super(actor, actorStateListener);
    }

    @Override
    public Animation getAnimation() {
        if (actor.getVelocity().getY() != 0) {
            return actor.getAnimations().get(AnimationType.FALL);
        } else {
            return actor.getAnimations().get(AnimationType.LAND);
        }
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        actor.getVelocity().setY(25);
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.LEFT);
                actor.getVelocity().setX(-actor.getMovementSpeed());
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.RIGHT);
                actor.getVelocity().setX(actor.getMovementSpeed());
            }
        } else {
            if (actor.getVelocity().getX() != 0) {
                actor.getVelocity().setX(0);
            }
        }
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        getAnimation().update();
        float velocityY = actor.getVelocity().getY() + (World.GRAVITY * secondsSinceLastUpdate);
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
        actor.getVelocity().setY(velocityY);
        step(world, secondsSinceLastUpdate);
        if (isStandingOnSolidTile(world)) {
            actor.getVelocity().setY(0);
            onLanded();
        }
    }

    @Override
    public void onExit() {
        actor.getAnimations().get(AnimationType.FALL).reset();
        actor.getAnimations().get(AnimationType.LAND).reset();
    }

    private void onLanded() {
        if (actor.getVelocity().getX() != 0) {
            actorStateListener.onChangeState(new KnightRunningState(actor, actorStateListener));
        } else {
            if (getAnimation().equals(actor.getAnimations().get(AnimationType.LAND)) && getAnimation().isFinished()) {
                actorStateListener.onChangeState(new KnightIdleState(actor, actorStateListener));
            }
        }
    }

}
