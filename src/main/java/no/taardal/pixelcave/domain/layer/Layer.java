package no.taardal.pixelcave.domain.layer;

import no.taardal.pixelcave.domain.LayerType;

public abstract class Layer {

    private String name;
    private LayerType type;
    private int x;
    private int y;
    private boolean visible;

    public Layer(LayerType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LayerType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
