package no.taardal.pixelcave.domain;

import no.taardal.pixelcave.domain.layer.GameObjectLayer;
import no.taardal.pixelcave.domain.layer.TileLayer;

import java.util.List;
import java.util.Map;

public class World {

    public static final int NO_TILE_ID = 0;

    private Map<Integer, Tile> tiles;
    private Map<String, TileLayer> tileLayers;
    private Map<String, GameObjectLayer> gameObjectLayers;
    private List<TileSet> tileSets;
    private int width;
    private int height;
    private int nextObjectId;
    private int tileWidth;
    private int tileHeight;

    public Map<Integer, Tile> getTiles() {
        return tiles;
    }

    public void setTiles(Map<Integer, Tile> tiles) {
        this.tiles = tiles;
    }

    public Map<String, TileLayer> getTileLayers() {
        return tileLayers;
    }

    public void setTileLayers(Map<String, TileLayer> tileLayers) {
        this.tileLayers = tileLayers;
    }

    public Map<String, GameObjectLayer> getGameObjectLayers() {
        return gameObjectLayers;
    }

    public void setGameObjectLayers(Map<String, GameObjectLayer> gameObjectLayers) {
        this.gameObjectLayers = gameObjectLayers;
    }

    public List<TileSet> getTileSets() {
        return tileSets;
    }

    public void setTileSets(List<TileSet> tileSets) {
        this.tileSets = tileSets;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNextObjectId() {
        return nextObjectId;
    }

    public void setNextObjectId(int nextObjectId) {
        this.nextObjectId = nextObjectId;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

}
