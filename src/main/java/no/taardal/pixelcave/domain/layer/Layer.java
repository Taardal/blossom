package no.taardal.pixelcave.domain.layer;

public abstract class Layer {

    public enum Type {
        GAME_OBJECT_LAYER,
        TILE_LAYER
    }

    private String name;
    private Type type;
    private int x;
    private int y;
    private boolean visible;

    Layer(String name, Type type, int x, int y, boolean visible) {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
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
