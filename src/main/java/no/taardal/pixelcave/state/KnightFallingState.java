package no.taardal.pixelcave.state;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.statemachine.StateListener;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightFallingState extends MovementState<Knight> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightFallingState.class);
    private static final int TERMINAL_VELOCITY = 300;

    public KnightFallingState(Knight actor, StateListener stateListener) {
        super(actor, stateListener);
    }

    @Override
    public Animation getAnimation() {
        if (actor.getVelocity().getY() != 0) {
            return actor.getAnimations().get(Animation.Type.FALL);
        } else {
            return actor.getAnimations().get(Animation.Type.LAND);
        }
    }

    @Override
    public void onEntry() {
        LOGGER.info("Entered [{}]", toString());
        actor.setVelocity(actor.getVelocity().withY(25));
        Animation animation = getAnimation();
        float boundsX = actor.getDirection() == Direction.RIGHT ? actor.getX() : actor.getX() + actor.getWidth() - animation.getWidth();
        float boundsY = actor.getY() + actor.getHeight() - animation.getHeight();
        actor.setCollisionBounds(new Bounds.Builder()
                .setWidth(animation.getWidth())
                .setHeight(animation.getHeight())
                .setPosition(new Vector2f(boundsX, boundsY))
                .build());
    }

    @Override
    public void handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                if (actor.getDirection() != Direction.LEFT) {
                    actor.setDirection(Direction.LEFT);
                }
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                if (actor.getDirection() != Direction.RIGHT) {
                    actor.setDirection(Direction.RIGHT);
                }
            }
            int velocityX = actor.getDirection() == Direction.RIGHT ? actor.getMovementSpeed() : -actor.getMovementSpeed();
            actor.setVelocity(actor.getVelocity().withX(velocityX));
        } else {
            if (actor.getVelocity().getX() != 0) {
                actor.setVelocity(new Vector2f(0, actor.getVelocity().getY()));
            }
        }
    }

    @Override
    public void update(World world, float secondsSinceLastUpdate) {
        Bounds collisionBounds = actor.getCollisionBounds();
        float boundsX = actor.getDirection() == Direction.RIGHT ? actor.getX() : actor.getX() + actor.getWidth() - collisionBounds.getWidth();
        collisionBounds.setPosition(collisionBounds.getPosition().withX(boundsX));

        float velocityY = actor.getVelocity().getY() + (World.GRAVITY * secondsSinceLastUpdate);
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
        actor.setVelocity(actor.getVelocity().withY(velocityY));

        Vector2f nextCollisionBoundsPosition = collisionBounds.getPosition().add(actor.getVelocity().multiply(secondsSinceLastUpdate));
        nextCollisionBoundsPosition = checkHorizontalCollision(nextCollisionBoundsPosition, world);
        nextCollisionBoundsPosition = checkVerticalCollision(nextCollisionBoundsPosition, world);
        Vector2f distanceToMove = nextCollisionBoundsPosition.subtract(collisionBounds.getPosition());
        actor.getSpriteBounds().setPosition(actor.getPosition().add(distanceToMove));
        collisionBounds.setPosition(collisionBounds.getPosition().add(distanceToMove));

        getAnimation().update();
        if (actor.getVelocity().getY() == 0) {
            if (actor.getVelocity().getX() != 0) {
                stateListener.onChangeState(new KnightRunningState(actor, stateListener));
            } else {
                if (getAnimation().isFinished()) {
                    stateListener.onChangeState(new KnightIdleState(actor, stateListener));
                }
            }
        }
    }

    @Override
    public void onExit() {
        actor.getAnimations().get(Animation.Type.FALL).reset();
        actor.getAnimations().get(Animation.Type.LAND).reset();
    }
}
