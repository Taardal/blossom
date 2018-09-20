package no.taardal.pixelcave.gameobject;

import java.util.Map;

public class GameObject {

    private String name;
    private String type;
    private Map<String, Object> properties;
    private int id;
    private int width;
    private int height;
    private float x;
    private float y;
    private float rotation;
    private boolean visible;

    public GameObject(String name, String type, Map<String, Object> properties, int id, int width, int height, float x, float y, float rotation, boolean visible) {
        this.name = name;
        this.type = type;
        this.properties = properties;
        this.id = id;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.visible = visible;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    public static final class Builder {

        private String name;
        private String type;
        private Map<String, Object> properties;
        private int id;
        private int width;
        private int height;
        private float x;
        private float y;
        private float rotation;
        private boolean visible;

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

        public Builder setId(int id) {
            this.id = id;
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
            this.x = x;
            return this;
        }

        public Builder setY(float y) {
            this.y = y;
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

        public GameObject createGameObject() {
            return new GameObject(name, type, properties, id, width, height, x, y, rotation, visible);
        }
    }

}
