package no.taardal.pixelcave.state;

import no.taardal.pixelcave.domain.Direction;
import no.taardal.pixelcave.domain.Tile;
import no.taardal.pixelcave.domain.Vector2f;
import no.taardal.pixelcave.domain.World;
import no.taardal.pixelcave.domain.GameActor;
import no.taardal.pixelcave.statemachine.StateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class MovementState {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovementState.class);

    GameActor gameActor;
    StateChangeListener stateChangeListener;

    MovementState(GameActor gameActor, StateChangeListener stateChangeListener) {
        this.gameActor = gameActor;
        this.stateChangeListener = stateChangeListener;
    }

    boolean isStandingOnSolidTile(World world) {
        int leftColumn = ((int) gameActor.getPosition().getX()) / world.getTileWidth();
        int rightColumn = (((int) gameActor.getPosition().getX()) + gameActor.getWidth() - 1) / world.getTileWidth();
        return getDistanceToClosestSolidTileBelow(leftColumn, rightColumn, world) <= 0;
    }

    void step(World world, float secondsSinceLastUpdate) {
        Vector2f nextPosition = gameActor.getPosition().add(gameActor.getVelocity().multiply(secondsSinceLastUpdate));
        nextPosition.setX(gameActor.getPosition().getX() + getDistanceToMoveX(nextPosition, world));
        nextPosition.setY(gameActor.getPosition().getY() + getDistanceToMoveY(nextPosition, world));
        gameActor.setPosition(nextPosition);
    }

    private float getDistanceToMoveX(Vector2f nextPosition, World world) {
        float distanceToMoveX = nextPosition.getX() - gameActor.getPosition().getX();
        float distanceToClosestSolidTileX = getDistanceToClosestSolidTileX(world);
        if (Math.abs(distanceToClosestSolidTileX) < Math.abs(distanceToMoveX)) {
            return distanceToClosestSolidTileX;
        } else {
            return distanceToMoveX;
        }
    }

    private float getDistanceToClosestSolidTileX(World world) {
        int topRow = ((int) gameActor.getPosition().getY()) / world.getTileHeight();
        int bottomRow = (((int) gameActor.getPosition().getY()) + gameActor.getHeight() - 1) / world.getTileHeight();
        if (gameActor.getDirection() == Direction.RIGHT) {
            return getDistanceToClosestSolidTileRight(topRow, bottomRow, world);
        } else {
            return getDistanceToClosestSolidTileLeft(topRow, bottomRow, world);
        }
    }

    private float getDistanceToClosestSolidTileRight(int topRow, int bottomRow, World world) {
        float collisionX = gameActor.getPosition().getX() + gameActor.getWidth();
        int collisionColumn = ((int) collisionX) / world.getTileWidth();
        float distanceToClosestTile = world.getWidth() * world.getTileWidth();
        for (int row = topRow; row <= bottomRow; row++) {
            for (int column = collisionColumn; column < world.getWidth(); column++) {
                if (isSolidTile(column, row, world)) {
                    int tileCollisionX = column * world.getTileWidth();
                    float distanceToTile = tileCollisionX - collisionX;
                    if (distanceToTile < distanceToClosestTile) {
                        distanceToClosestTile = distanceToTile;
                    }
                    break;
                }
            }
        }
        return distanceToClosestTile > 0 ? distanceToClosestTile : 0;
    }

    private float getDistanceToClosestSolidTileLeft(int topRow, int bottomRow, World world) {
        float collisionX = gameActor.getPosition().getX();
        int collisionColumn = ((int) collisionX) / world.getTileWidth();
        float distanceToClosestTile = -collisionX;
        for (int row = topRow; row <= bottomRow; row++) {
            for (int column = collisionColumn; column >= 0; column--) {
                if (isSolidTile(column, row, world)) {
                    int tileCollisionX = (column * world.getTileWidth()) + world.getTileWidth();
                    float distanceToTile = tileCollisionX - collisionX;
                    if (Math.abs(distanceToTile) < Math.abs(distanceToClosestTile)) {
                        distanceToClosestTile = distanceToTile;
                    }
                    break;
                }
            }
        }
        return Math.abs(distanceToClosestTile) > 0 ? distanceToClosestTile : 0;
    }

    private float getDistanceToMoveY(Vector2f nextPosition, World world) {
        float distanceToMoveY = nextPosition.getY() - gameActor.getPosition().getY();
        float distanceToClosestSolidTileY = getDistanceToClosestSolidTileY(world);
        if (Math.abs(distanceToClosestSolidTileY) < Math.abs(distanceToMoveY)) {
            return distanceToClosestSolidTileY;
        } else {
            return distanceToMoveY;
        }
    }

    private float getDistanceToClosestSolidTileY(World world) {
        int leftColumn = ((int) gameActor.getPosition().getX()) / world.getTileWidth();
        int rightColumn = (((int) gameActor.getPosition().getX()) + gameActor.getWidth() - 1) / world.getTileWidth();
        if (gameActor.getVelocity().getY() < 0) {
            return getDistanceToClosestSolidTileAbove(leftColumn, rightColumn, world);
        } else {
            return getDistanceToClosestSolidTileBelow(leftColumn, rightColumn, world);
        }
    }

    private float getDistanceToClosestSolidTileBelow(int leftColumn, int rightColumn, World world) {
        float collisionY = gameActor.getPosition().getY() + gameActor.getHeight();
        int collisionRow = ((int) collisionY) / world.getTileHeight();
        float distanceToClosestTile = world.getHeight() * world.getTileHeight();
        for (int column = leftColumn; column <= rightColumn; column++) {
            for (int row = collisionRow; row < world.getHeight(); row++) {
                if (isSolidTile(column, row, world)) {
                    int tileCollisionY = row * world.getTileHeight();
                    float distanceToTile = tileCollisionY - collisionY;
                    if (distanceToTile < distanceToClosestTile) {
                        distanceToClosestTile = distanceToTile;
                    }
                    break;
                }
            }
        }
        return distanceToClosestTile > 0 ? distanceToClosestTile : 0;
    }

    private float getDistanceToClosestSolidTileAbove(int leftColumn, int rightColumn, World world) {
        float collisionY = gameActor.getPosition().getY();
        int collisionRow = ((int) collisionY) / world.getTileHeight();
        float distanceToClosesTile = -collisionY;
        for (int column = leftColumn; column <= rightColumn; column++) {
            for (int row = collisionRow; row >= 0; row--) {
                int tileCollisionY = (row * world.getTileHeight()) + world.getTileHeight();
                float distanceToTile = tileCollisionY - collisionY;
                if (isSolidTile(column, row, world)) {
                    if (Math.abs(distanceToTile) < Math.abs(distanceToClosesTile)) {
                        distanceToClosesTile = distanceToTile;
                    }
                    break;
                }
            }
        }
        return Math.abs(distanceToClosesTile) > 0 ? distanceToClosesTile : 0;
    }

    private boolean isSolidTile(int column, int row, World world) {
        Tile tile = getTile(column, row, world);
        //return tile != null && !tile.isSlope();
        return true;
    }

    private Tile getTile(int column, int row, World world) {
        /*TileLayer tileLayer = (TileLayer) world.getLayers().get("main");
        int tileId = tileLayer.getTileGrid()[column][row];
        return world.getTiles().get(tileId);*/
        return null;
    }

}
