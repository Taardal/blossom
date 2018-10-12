package no.taardal.pixelcave.domain.layer;

import no.taardal.pixelcave.domain.LayerType;

public class TileLayer extends Layer {

    private int[][] tileGrid;
    private int width;
    private int height;

    public TileLayer() {
        super(LayerType.TILE_LAYER);
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

}
