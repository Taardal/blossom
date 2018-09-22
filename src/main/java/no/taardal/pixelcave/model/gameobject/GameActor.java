package no.taardal.pixelcave.model.gameobject;

import no.taardal.pixelcave.model.Direction;
import no.taardal.pixelcave.model.Vector2f;
import no.taardal.pixelcave.model.animation.Animation;
import no.taardal.pixelcave.model.animation.AnimationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GameActor extends GameObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameActor.class);

    private Map<AnimationType, Animation> animations;
    private Direction direction;
    private Vector2f velocity;

    private GameActor(int id, String name, String type, Map<String, Object> properties, Vector2f position, int width, int height, float rotation, boolean visible, Map<AnimationType, Animation> animations, Direction direction, Vector2f velocity) {
        super(id, name, type, properties, position, width, height, rotation, visible);
        this.animations = animations;
        this.direction = direction;
        this.velocity = velocity;
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

    @Override
    public GameActor.Builder toBuilder() {
        return new Builder()
                .setId(id)
                .setName(name)
                .setType(type)
                .setProperties(properties)
                .setPosition(position)
                .setWidth(width)
                .setHeight(height)
                .setRotation(rotation)
                .setVisible(visible)
                .setAnimations(animations)
                .setDirection(direction)
                .setVelocity(velocity);
    }

    public static class Builder extends GameObject.Builder {

        private Map<AnimationType, Animation> animations;
        private Direction direction;
        private Vector2f velocity;

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

        @Override
        public Builder setId(int id) {
            super.setId(id);
            return this;
        }

        @Override
        public Builder setName(String name) {
            super.setName(name);
            return this;
        }

        @Override
        public Builder setType(String type) {
            super.setType(type);
            return this;
        }

        @Override
        public Builder setProperties(Map<String, Object> properties) {
            super.setProperties(properties);
            return this;
        }

        @Override
        public Builder setPosition(Vector2f position) {
            super.setPosition(position);
            return this;
        }

        @Override
        public Builder setWidth(int width) {
            super.setWidth(width);
            return this;
        }

        @Override
        public Builder setHeight(int height) {
            super.setHeight(height);
            return this;
        }

        @Override
        public Builder setX(float x) {
            super.setX(x);
            return this;
        }

        @Override
        public Builder setY(float y) {
            super.setY(y);
            return this;
        }

        @Override
        public Builder setRotation(float rotation) {
            super.setRotation(rotation);
            return this;
        }

        @Override
        public Builder setVisible(boolean visible) {
            super.setVisible(visible);
            return this;
        }

        public GameActor build() {
            return new GameActor(id, name, type, properties, position, width, height, rotation, visible, animations, direction, velocity);
        }
    }

}
