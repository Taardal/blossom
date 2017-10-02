package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.animation.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.state.actor.ActorIdleState;
import no.taardal.blossom.statemachine.StateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightIdleState extends ActorIdleState<Knight> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightIdleState.class);

    public KnightIdleState(Knight actor, StateMachine stateMachine) {
        super(actor, stateMachine);
    }

    @Override
    public void nextMove(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            stateMachine.changeState(new KnightRunningState(actor, stateMachine));
        } else if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            stateMachine.changeState(new KnightJumpingState(actor, stateMachine));
        } else if (keyboard.isPressed(KeyBinding.CROUCH)) {

        } else if (keyboard.isPressed(KeyBinding.TUMBLE)) {
        } else if (keyboard.isPressed(KeyBinding.DEFEND)) {
        }
    }

    @Override
    protected Animation getActorAnimation() {
        Sprite[] sprites = new Sprite[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = actor.getSpriteSheet().getSprites()[i][0];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(10);
        return animation;
    }

    @Override
    protected void updateBounds() {
        actor.getBounds().setWidth(19);
        actor.getBounds().setHeight(30);
        actor.getBounds().setPosition(getBoundsX(), getBoundsY());
    }

    private int getBoundsY() {
        return (actor.getY() + actor.getHeight()) - actor.getBounds().getHeight();
    }

    private int getBoundsX() {
        int marginX = 5;
        if (actor.getDirection() == Direction.EAST) {
            return actor.getX() + marginX;
        } else {
            return actor.getX() + actor.getWidth() - actor.getBounds().getWidth() - marginX;
        }
    }

}
