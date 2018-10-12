package no.taardal.pixelcave.domain.gameobject;

import no.taardal.pixelcave.domain.Vector2f;

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

    GameObject() {
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
        position.setX(x);
    }

    public float getY() {
        return position.getY();
    }

    public void setY(float y) {
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

    public static class Builder {

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

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setProperties(Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }

        public Builder setPosition(Vector2f position) {
            this.position = position;
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

        public Builder setX(float x) {
            if (position != null) {
                position.setX(x);
            } else {
                position = new Vector2f(x, 0);
            }
            return this;
        }

        public Builder setY(float y) {
            if (position != null) {
                position.setY(y);
            } else {
                position = new Vector2f(0, y);
            }
            return this;
        }

        public Builder setRotation(float rotation) {
            this.rotation = rotation;
            return this;
        }

        public Builder setVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder setControllable(boolean controllable) {
            this.controllable = controllable;
            return this;
        }

        public GameObject build() {
            return new GameObject(id, name, type, properties, position, width, height, rotation, visible, controllable);
        }
    }

}
