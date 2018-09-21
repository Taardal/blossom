package no.taardal.pixelcave.actor;

import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.animation.AnimationType;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.vector.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    Map<AnimationType, Animation> animations;
    Direction direction;
    Vector2f velocity;
    Vector2f position;
    int width;
    int height;

    private Actor(Map<AnimationType, Animation> animations, Direction direction, Vector2f velocity, Vector2f position, int width, int height) {
        this.animations = animations;
        this.direction = direction;
        this.velocity = velocity;
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Map<AnimationType, Animation> getAnimations() {
        return animations;
    }

    public Direction getDirection() {
        return direction;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public Vector2f getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Builder toBuilder() {
        return new Builder()
                .setAnimations(animations)
                .setDirection(direction)
                .setVelocity(velocity)
                .setPosition(position)
                .setWidth(width)
                .setHeight(height);
    }

    public static final class Builder {

        private Map<AnimationType, Animation> animations;
        private Direction direction;
        private Vector2f velocity;
        private Vector2f position;
        private int width;
        private int height;

        public Builder setAnimations(Map<AnimationType, Animation> animations) {
            this.animations = animations;
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

        public Actor build() {
            return new Actor(animations, direction, velocity, position, width, height);
        }
    }

}
