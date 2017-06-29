package no.taardal.blossom.entity;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.LayerType;
import no.taardal.blossom.world.World;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.state.actorstate.ActorState;
import no.taardal.blossom.state.actorstate.FallingActorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Actor extends Entity {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);
    private final Layer environmentLayer;

    protected AnimatedSprite sprite;
    protected World world;
    protected Rectangle boundingBox;
    protected Direction direction;
    protected boolean falling;
    protected boolean moving;

    protected ActorState actorState;

    public Actor(AnimatedSprite sprite, World world) {
        this.sprite = sprite;
        this.world = world;
        falling = true;
        actorState = new FallingActorState(this, world);

        environmentLayer = world.getLayers().stream()
                .filter(tiledEditorLayer -> tiledEditorLayer.getLayerType() == LayerType.TILELAYER && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer"))
                .findFirst()
                .orElse(null);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public AnimatedSprite getAnimatedSprite() {
        return sprite;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public void update(Keyboard keyboard) {
        ActorState actorState = this.actorState.update();
        if (actorState != null) {
            LOGGER.debug("New actor state [{}]", actorState.toString());
            this.actorState = actorState;
        }
        sprite.update();
        boundingBox.setLocation(getX(), getY());
    }

    @Override
    public void draw(Camera camera) {
        sprite.draw(getX(), getY(), direction, camera);
    }

    public boolean isOnGround() {
        int column = x / world.getTileWidth();
        int row = (y + getHeight()) / world.getTileHeight();
        return environmentLayer.getTileGrid()[column][row] != World.NO_TILE_ID;
    }

}
