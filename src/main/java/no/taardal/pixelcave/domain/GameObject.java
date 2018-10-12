package no.taardal.pixelcave.domain;

import java.util.Map;

public class GameObject {

    int id;
    String name;
    String type;
    Map<String, Object> properties;
    Vector2f position;
    int width;
    int height;
    float rotation;
    boolean visible;
    boolean controllable;

    public GameObject() {
    }

    private GameObject(int id, String name, String type, Map<String, Object> properties, Vector2f position, int width, int height, float rotation, boolean visible, boolean controllable) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.properties = properties;
        this.position = position;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.visible = visible;
        this.controllable = controllable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getX() {
        return position.getX();
    }

    public void setX(float x) {
        if (position == null) {
            position = Vector2f.zero();
        }
        position.setX(x);
    }

    public float getY() {
        return position.getY();
    }

    public void setY(float y) {
        if (position == null) {
            position = Vector2f.zero();
        }
        position.setY(y);
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

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isControllable() {
        return controllable;
    }

    public void setControllable(boolean controllable) {
        this.controllable = controllable;
    }

}
