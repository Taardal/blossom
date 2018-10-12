package no.taardal.pixelcave.domain.layer;

import no.taardal.pixelcave.domain.gameobject.GameObject;

import java.util.List;

public class GameObjectLayer extends Layer {

    private List<GameObject> gameObjects;

    public GameObjectLayer(String name, int x, int y, boolean visible, List<GameObject> gameObjects) {
        super(name, Type.GAME_OBJECT_LAYER, x, y, visible);
        this.gameObjects = gameObjects;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public static final class Builder {

        private String name;
        private int x;
        private int y;
        private boolean visible;
        private List<GameObject> gameObjects;

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

        public Builder setGameObjects(List<GameObject> gameObjects) {
            this.gameObjects = gameObjects;
            return this;
        }

        public GameObjectLayer build() {
            return new GameObjectLayer(name, x, y, visible, gameObjects);
        }
    }

}
