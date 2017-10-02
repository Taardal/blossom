package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.animation.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.statemachine.StateMachine;
import no.taardal.blossom.vector.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightJumpingState extends KnightFallingState {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightJumpingState.class);
    private static final int VELOCITY_Y = -200;

    private Animation jumpingAnimation;

    public KnightJumpingState(Knight actor, StateMachine stateMachine) {
        super(actor, stateMachine);
        jumpingAnimation = getJumpingActorAnimation();
    }

    @Override
    public Animation getAnimation() {
        if (falling && actor.getVelocity().getY() < 100) {
            return jumpingAnimation;
        } else {
            return super.getAnimation();
        }
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setVelocity(new Vector2d(actor.getVelocity().getX(), VELOCITY_Y));
    }

    @Override
    public void onExit() {
        jumpingAnimation.reset();
        super.onExit();
    }

    @Override
    protected void updateBounds() {
        actor.getBounds().setWidth(19);
        actor.getBounds().setHeight(30);
        if (falling && actor.getVelocity().getY() < 100) {
            actor.getBounds().setPosition(actor.getBounds().getX(), actor.getBounds().getY() - 5);
        }
    }

    private Animation getJumpingActorAnimation() {
        Sprite[] sprites = new Sprite[6];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = actor.getSpriteSheet().getSprites()[i][10];
        }
        Animation animation = new Animation(sprites);
        animation.setIndefinite(false);
        return animation;
    }

}
