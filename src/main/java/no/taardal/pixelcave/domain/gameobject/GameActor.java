package no.taardal.pixelcave.domain.gameobject;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.domain.Direction;
import no.taardal.pixelcave.domain.Vector2f;
import no.taardal.pixelcave.domain.World;
import no.taardal.pixelcave.domain.animation.Animation;
import no.taardal.pixelcave.domain.animation.AnimationType;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.state.GameActorState;
import no.taardal.pixelcave.statemachine.StateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GameActor extends GameObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameActor.class);

    private StateMachine<GameActorState> movementStateMachine;
    private Map<AnimationType, Animation> animations;
    private GameActorType gameActorType;
    private Direction direction;
    private Vector2f velocity;

    public StateMachine<GameActorState> getMovementStateMachine() {
        return movementStateMachine;
    }

    public void setMovementStateMachine(StateMachine<GameActorState> movementStateMachine) {
        this.movementStateMachine = movementStateMachine;
    }

    public Map<AnimationType, Animation> getAnimations() {
        return animations;
    }

    public void setAnimations(Map<AnimationType, Animation> animations) {
        this.animations = animations;
    }

    public Direction getDirection() {
        return direction;
    }

    public GameActorType getGameActorType() {
        return gameActorType;
    }

    public void setGameActorType(GameActorType gameActorType) {
        this.gameActorType = gameActorType;
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

    public void handleInput(Keyboard keyboard) {
        if (movementStateMachine != null && !movementStateMachine.isEmpty()) {
            movementStateMachine.getCurrentState().handleInput(keyboard);
        }
    }

    public void update(World world, float secondsSinceLastUpdate) {
        if (movementStateMachine != null && !movementStateMachine.isEmpty()) {
            movementStateMachine.getCurrentState().update(world, secondsSinceLastUpdate);
        }
    }

    public void draw(Camera camera) {
        if (movementStateMachine != null && !movementStateMachine.isEmpty()) {
            Animation animation = movementStateMachine.getCurrentState().getAnimation();
            animation.draw(this, camera);
        }
    }

}
