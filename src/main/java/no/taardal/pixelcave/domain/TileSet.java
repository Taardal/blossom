package no.taardal.pixelcave.domain;

import java.util.List;

public class TileSet {

    private List<Tile> tiles;
    private String name;
    private String imagePath;
    private int firstGlobalId;
    private int numberOfColumns;
    private int numberOfTiles;
    private int tileWidth;
    private int imageWidth;
    private int imageHeight;
    private int tileHeight;
    private int margin;
    private int spacing;

    public TileSet(List<Tile> tiles, String name, String imagePath, int firstGlobalId, int numberOfColumns, int numberOfTiles, int tileWidth, int imageWidth, int imageHeight, int tileHeight, int margin, int spacing) {
        this.tiles = tiles;
        this.name = name;
        this.imagePath = imagePath;
        this.firstGlobalId = firstGlobalId;
        this.numberOfColumns = numberOfColumns;
        this.numberOfTiles = numberOfTiles;
        this.tileWidth = tileWidth;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.tileHeight = tileHeight;
        this.margin = margin;
        this.spacing = spacing;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getFirstGlobalId() {
        return firstGlobalId;
    }

    public void setFirstGlobalId(int firstGlobalId) {
        this.firstGlobalId = firstGlobalId;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfTiles() {
        return numberOfTiles;
    }

    public void setNumberOfTiles(int numberOfTiles) {
        this.numberOfTiles = numberOfTiles;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public static final class Builder {

        private List<Tile> tiles;
        private String name;
        private String imagePath;
        private int firstGlobalId;
        private int numberOfColumns;
        private int numberOfTiles;
        private int tileWidth;
        private int imageWidth;
        private int imageHeight;
        private int tileHeight;
        private int margin;
        private int spacing;

        public Builder setTiles(List<Tile> tiles) {
            this.tiles = tiles;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder setFirstGlobalId(int firstGlobalId) {
            this.firstGlobalId = firstGlobalId;
            return this;
        }

        public Builder setNumberOfColumns(int numberOfColumns) {
            this.numberOfColumns = numberOfColumns;
            return this;
        }

        public Builder setNumberOfTiles(int numberOfTiles) {
            this.numberOfTiles = numberOfTiles;
            return this;
        }

        public Builder setTileWidth(int tileWidth) {
            this.tileWidth = tileWidth;
            return this;
        }

        public Builder setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public Builder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public Builder setTileHeight(int tileHeight) {
            this.tileHeight = tileHeight;
            return this;
        }

        public Builder setMargin(int margin) {
            this.margin = margin;
            return this;
        }

        public Builder setSpacing(int spacing) {
            this.spacing = spacing;
            return this;
        }

        public TileSet build() {
            return new TileSet(tiles, name, imagePath, firstGlobalId, numberOfColumns, numberOfTiles, tileWidth, imageWidth, imageHeight, tileHeight, margin, spacing);
        }
    }

}
