package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.bounds.Bounds;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.animation.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.state.actor.ActorAttackingState;
import no.taardal.blossom.statemachine.StateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightAttackingState extends ActorAttackingState<Knight> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightAttackingState.class);

    public KnightAttackingState(Knight actor, StateMachine stateMachine) {
        super(actor, stateMachine);
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        if (getAnimation().isFinished()) {
            stateMachine.popState();
        } else if (!enemiesAttacked && getAnimation().getFrame() == 4) {
            attackEnemiesInRange();
            enemiesAttacked = true;
        }
        super.update(secondsSinceLastUpdate);
    }

    @Override
    protected Animation getActorAnimation() {
        Sprite[] sprites = new Sprite[9];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = actor.getSpriteSheet().getSprites()[i][1];
        }
        Animation animation = new Animation(sprites);
        animation.setUpdatesPerFrame(3);
        animation.setIndefinite(false);
        return animation;
    }

    protected Bounds getAttackBounds() {
        Bounds bounds = new Bounds();
        bounds.setWidth(actor.getAttackRange());
        bounds.setHeight(actor.getBounds().getHeight() / 2 + 5);
        bounds.setX(getAttackBoundsX());
        bounds.setY(actor.getBounds().getY());
        return bounds;
    }

    private int getAttackBoundsX() {
        if (actor.getDirection() == Direction.EAST) {
            return actor.getBounds().getX() + (actor.getBounds().getWidth() / 2);
        } else {
            return actor.getBounds().getX() + (actor.getBounds().getWidth() / 2) - actor.getAttackRange();
        }
    }

}
