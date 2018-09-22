package no.taardal.pixelcave.level;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.model.Ribbon;
import no.taardal.pixelcave.model.Tile;
import no.taardal.pixelcave.model.World;
import no.taardal.pixelcave.model.animation.Animation;
import no.taardal.pixelcave.model.animation.AnimationType;
import no.taardal.pixelcave.model.gameobject.GameActor;
import no.taardal.pixelcave.model.layer.Layer;
import no.taardal.pixelcave.model.layer.TileLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.IntStream;

public class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    private World world;
    private List<Ribbon> ribbons;
    private GameActor player;
    private List<GameActor> enemies;

    public Level(World world, List<Ribbon> ribbons, GameActor player, List<GameActor> enemies) {
        this.world = world;
        this.ribbons = ribbons;
        this.player = player;
        this.enemies = enemies;
    }

    public void updateRibbons(Camera camera) {
        IntStream.range(0, ribbons.size()).forEach(i -> ribbons.get(i).update(camera.getDirection()));
    }

    public void drawRibbons(Camera camera) {
        IntStream.range(0, ribbons.size()).forEach(i -> ribbons.get(i).draw(camera));
    }

    public void drawTiles(Camera camera) {
        world.getTileLayers().values().stream().filter(Layer::isVisible).forEach(tileLayer -> drawTiles(tileLayer, camera));
    }

    public void drawGameActors(Camera camera) {
        enemies.forEach(gameActor -> {
            Animation animation = gameActor.getAnimations().get(AnimationType.IDLE);
            animation.draw(gameActor, camera, false);
        });
        Animation animation = player.getAnimations().get(AnimationType.IDLE);
        animation.draw(player, camera, false);
    }

    private void drawTiles(TileLayer tileLayer, Camera camera) {
        int topMostRowToDraw = (camera.getY() - world.getTileHeight()) / world.getTileHeight();
        int bottomMostRowToDraw = (camera.getY() + camera.getHeight() + world.getTileHeight()) / world.getTileHeight();
        int leftMostColumnToDraw = (camera.getX() - world.getTileWidth()) / world.getTileWidth();
        int rightMostColumnToDraw = (camera.getX() + camera.getWidth() + world.getTileWidth()) / world.getTileWidth();
        for (int row = topMostRowToDraw; row < bottomMostRowToDraw; row++) {
            if (row < 0) {
                continue;
            }
            if (row >= world.getHeight()) {
                break;
            }
            for (int column = leftMostColumnToDraw; column < rightMostColumnToDraw; column++) {
                if (column < 0) {
                    continue;
                }
                if (column >= world.getWidth()) {
                    break;
                }
                drawTile(row, column, tileLayer, camera);
            }
        }
    }

    private void drawTile(int row, int column, TileLayer tileLayer, Camera camera) {
        int tileId = tileLayer.getTileGrid()[column][row];
        if (tileId != World.NO_TILE_ID) {
            Tile tile = world.getTiles().get(tileId);
            if (tile != null) {
                int x = column * world.getTileWidth();
                int y = row * world.getTileHeight();
                tile.draw(x, y, camera);
            }
        }
    }
}
