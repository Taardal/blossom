package no.taardal.pixelcave.level;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.domain.GameActor;
import no.taardal.pixelcave.domain.Tile;
import no.taardal.pixelcave.domain.World;
import no.taardal.pixelcave.domain.layer.Layer;
import no.taardal.pixelcave.domain.layer.TileLayer;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.ribbon.Ribbon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    private World world;
    private List<Ribbon> ribbons;
    private List<GameActor> gameActors;

    public Level(World world, List<Ribbon> ribbons, List<GameActor> gameActors) {
        this.world = world;
        this.ribbons = ribbons;
        this.gameActors = gameActors;
    }

    public void handlePlayerInput(Keyboard keyboard) {
        gameActors.stream().filter(this::isPlayer).forEach(gameActor -> gameActor.handleInput(keyboard));
    }

    public void updateRibbons(Camera camera) {
        ribbons.forEach(ribbon -> ribbon.update(camera.getDirection()));
    }

    public void updateGameActors(float secondsSinceLastUpdate) {
        gameActors.forEach(gameActor -> gameActor.update(world, secondsSinceLastUpdate));
    }

    public void drawRibbons(Camera camera) {
        ribbons.forEach(ribbon -> ribbon.draw(camera));
    }

    public void drawTiles(Camera camera) {
        world.getTileLayers().values().stream().filter(Layer::isVisible).forEach(tileLayer -> drawTiles(tileLayer, camera));
    }

    public void drawGameActors(Camera camera) {
        gameActors.forEach(gameActor -> gameActor.draw(camera));
    }

    private boolean isPlayer(GameActor gameActor) {
        return gameActor.getName().equalsIgnoreCase("player");
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
