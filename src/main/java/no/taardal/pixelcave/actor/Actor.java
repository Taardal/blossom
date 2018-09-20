package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.animation.AnimationType;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.statemachine.ActorActorStateMachine;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    ActorActorStateMachine actorStateMachine;
    SpriteSheet spriteSheet;
    Direction direction;
    Vector2f velocity;
    Vector2f position;
    Map<AnimationType, Animation> animations;
    int width;
    int height;
    int movementSpeed;

    private Actor() {
        actorStateMachine = new ActorActorStateMachine();
    }

    public Actor(SpriteSheet spriteSheet, Direction direction, Vector2f velocity, Vector2f position) {
        this();
        this.spriteSheet = spriteSheet;
        this.direction = direction;
        this.velocity = velocity;
        this.position = position;
        animations = createAnimations();
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Map<AnimationType, Animation> getAnimations() {
        return animations;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void handleInput(Keyboard keyboard) {
        if (!actorStateMachine.isEmpty()) {
            actorStateMachine.getCurrentState().handleInput(keyboard);
        } else {
            LOGGER.warn("Could not handle input. State machine was empty.");
        }
    }

    public void update(World world, float secondsSinceLastUpdate) {
        if (!actorStateMachine.isEmpty()) {
            actorStateMachine.getCurrentState().update(world, secondsSinceLastUpdate);
        } else {
            LOGGER.warn("Could not update. State machine was empty.");
        }
    }

    public void draw(Camera camera) {
        Animation animation = actorStateMachine.isEmpty() ? null : actorStateMachine.getCurrentState().getAnimation();
        if (animation != null) {
            animation.draw(this, camera, isFlipped());
        } else {
            LOGGER.warn("Could not draw. Animation was null.");
        }
    }

    Map<AnimationType, Animation> createAnimations() {
        return Collections.emptyMap();
    }

    ;

    boolean isFlipped() {
        return false;
    }

    ;

    public void setAnimations(Map<AnimationType, Animation> animations) {

    }

    public Actor copy() {
        return null;
    }

    public Builder toBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private Map<AnimationType, Animation> animations;
        private SpriteSheet spriteSheet;
        private Direction direction;
        private Vector2f velocity;
        private Vector2f position;
        private int width;
        private int height;

        public Builder setAnimations(Map<AnimationType, Animation> animations) {
            this.animations = animations;
            return this;
        }

        public Builder setSpriteSheet(SpriteSheet spriteSheet) {
            this.spriteSheet = spriteSheet;
            return this;
        }

        public Builder setDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder setVelocity(Vector2f velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder setPosition(Vector2f position) {
            this.position = position;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Actor createActor() {
            return new Actor(spriteSheet, direction, velocity, position);
        }
    }

}
