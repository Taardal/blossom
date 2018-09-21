package no.taardal.pixelcave.level;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.layer.Layer;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.IntStream;

public class Level {

    private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

    private World world;
    private List<Ribbon> ribbons;
    private Actor player;
    private List<Actor> enemies;

    public Level(World world, List<Ribbon> ribbons, Actor player, List<Actor> enemies) {
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
