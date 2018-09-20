package no.taardal.pixelcave.layer;

public class TileLayer extends Layer {

    private int[][] tileGrid;
    private int width;
    private int height;

    public TileLayer(String name, int x, int y, boolean visible, int[][] tileGrid, int width, int height) {
        super(name, Type.TILE_LAYER, x, y, visible);
        this.tileGrid = tileGrid;
        this.width = width;
        this.height = height;
    }

    public int[][] getTileGrid() {
        return tileGrid;
    }

    public void setTileGrid(int[][] tileGrid) {
        this.tileGrid = tileGrid;
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

    public static final class Builder {

        private String name;
        private int x;
        private int y;
        private boolean visible;
        private int[][] tileGrid;
        private int width;
        private int height;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setX(int x) {
            this.x = x;
            return this;
        }

        public Builder setY(int y) {
            this.y = y;
            return this;
        }

        public Builder setVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder setTileGrid(int[][] tileGrid) {
            this.tileGrid = tileGrid;
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

        public TileLayer createTileLayer() {
            return new TileLayer(name, x, y, visible, tileGrid, width, height);
        }
    }

}
