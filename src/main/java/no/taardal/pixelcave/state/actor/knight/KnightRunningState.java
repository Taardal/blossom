package no.taardal.pixelcave.state.actor.knight;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.keyboard.KeyBinding;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.sprite.Sprite;
import no.taardal.pixelcave.state.actor.ActorRunningState;
import no.taardal.pixelcave.statemachine.StateMachine;

public class KnightRunningState extends ActorRunningState<Knight> {

    public KnightRunningState(Knight actor, StateMachine stateMachine) {
        super(actor, stateMachine);
    }

    @Override
    public void nextMove(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.WEST);
                actor.setVelocity(getVelocity());
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(getVelocity());
            }
        } else {
            stateMachine.changeState(new KnightIdleState(actor, stateMachine));
        }
        if (keyboard.isPressed(KeyBinding.UP_MOVEMENT)) {
            stateMachine.changeState(new KnightJumpingState(actor, stateMachine));
        }
        if (keyboard.isPressed(KeyBinding.CROUCH)) {
        }
        if (keyboard.isPressed(KeyBinding.TUMBLE)) {
        }
        if (keyboard.isPressed(KeyBinding.DEFEND)) {
        }
    }

    @Override
    protected Animation getActorAnimation() {
        Sprite[] sprites = new Sprite[10];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = actor.getSpriteSheet().getSprites()[i][8];
        }
        return new Animation(sprites);
    }

    @Override
    protected void updateBounds() {
        actor.getBounds().setWidth(27);
        actor.getBounds().setHeight(27);
        actor.getBounds().setPosition(getBoundsX(), getBoundsY());
    }

    private int getBoundsY() {
        return (actor.getY() + actor.getHeight()) - actor.getBounds().getHeight();
    }

    private int getBoundsX() {
        int marginX = 8;
        if (actor.getDirection() == Direction.EAST) {
            return actor.getX() + marginX;
        } else {
            return actor.getX() + actor.getWidth() - actor.getBounds().getWidth() - marginX;
        }
    }

}