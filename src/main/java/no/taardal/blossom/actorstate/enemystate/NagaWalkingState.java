package no.taardal.blossom.actorstate.enemystate;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actorstate.ActorWalkingState;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.world.World;

import java.awt.*;

public class NagaWalkingState extends ActorWalkingState<Naga> implements EnemyState {

    private static final Animation WALKING_ANIMATION = getWalkingAnimation();
    private static final Rectangle BOUNDS = new Rectangle(20, 34);

    public NagaWalkingState(Naga actor, World world) {
        super(actor, world);
    }

    @Override
    public Animation getAnimation() {
        return WALKING_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return BOUNDS;
    }

    @Override
    public void nextMove() {

    }

    @Override
    protected void updateBounds() {
        int boundsX = actor.getX() + 22;
        int boundsY = (actor.getY() + actor.getHeight()) - (int) BOUNDS.getHeight();
        BOUNDS.setLocation(boundsX, boundsY);
    }

    @Override
    protected int getVelocityX() {
        return 20;
    }

    private static Animation getWalkingAnimation() {
        Sprite[] sprites = new Sprite[7];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = Naga.SPRITE_SHEET.getSprites()[i][1];
        }
        Animation animation = new Animation(sprites);
        return animation;
    }

}
