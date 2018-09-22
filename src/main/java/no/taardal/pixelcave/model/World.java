package no.taardal.pixelcave.model;

import no.taardal.pixelcave.model.layer.GameObjectLayer;
import no.taardal.pixelcave.model.layer.TileLayer;

import java.util.List;
import java.util.Map;

public class World {

    public static final int NO_TILE_ID = 0;
    public static final int GRAVITY = 500;

    private Map<Integer, Tile> tiles;
    private Map<String, TileLayer> tileLayers;
    private Map<String, GameObjectLayer> gameObjectLayers;
    private List<TileSet> tileSets;
    private int width;
    private int height;
    private int nextObjectId;
    private int tileWidth;
    private int tileHeight;

    public World(Map<Integer, Tile> tiles, Map<String, TileLayer> tileLayers, Map<String, GameObjectLayer> gameObjectLayers, List<TileSet> tileSets, int width, int height, int nextObjectId, int tileWidth, int tileHeight) {
        this.tiles = tiles;
        this.tileLayers = tileLayers;
        this.gameObjectLayers = gameObjectLayers;
        this.tileSets = tileSets;
        this.width = width;
        this.height = height;
        this.nextObjectId = nextObjectId;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

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

    public static final class Builder {

        private Map<Integer, Tile> tiles;
        private Map<String, TileLayer> tileLayers;
        private Map<String, GameObjectLayer> gameObjectLayers;
        private List<TileSet> tileSets;
        private int width;
        private int height;
        private int nextObjectId;
        private int tileWidth;
        private int tileHeight;

        public Builder setTiles(Map<Integer, Tile> tiles) {
            this.tiles = tiles;
            return this;
        }

        public Builder setTileLayers(Map<String, TileLayer> tileLayers) {
            this.tileLayers = tileLayers;
            return this;
        }

        public Builder setGameObjectLayers(Map<String, GameObjectLayer> gameObjectLayers) {
            this.gameObjectLayers = gameObjectLayers;
            return this;
        }

        public Builder setTileSets(List<TileSet> tileSets) {
            this.tileSets = tileSets;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setNextObjectId(int nextObjectId) {
            this.nextObjectId = nextObjectId;
            return this;
        }

        public Builder setTileWidth(int tileWidth) {
            this.tileWidth = tileWidth;
            return this;
        }

        public Builder setTileHeight(int tileHeight) {
            this.tileHeight = tileHeight;
            return this;
        }

        public World createWorld() {
            return new World(tiles, tileLayers, gameObjectLayers, tileSets, width, height, nextObjectId, tileWidth, tileHeight);
        }
    }


}
