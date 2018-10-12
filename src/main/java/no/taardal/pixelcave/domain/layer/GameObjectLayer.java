package no.taardal.pixelcave.domain.layer;

import no.taardal.pixelcave.domain.GameObject;
import no.taardal.pixelcave.domain.LayerType;

import java.util.List;

public class GameObjectLayer extends Layer {

    private List<GameObject> gameObjects;

    public GameObjectLayer() {
        super(LayerType.GAME_OBJECT_LAYER);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

}
