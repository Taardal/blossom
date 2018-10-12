package no.taardal.pixelcave.jsondeserializer;

import com.google.gson.*;
import no.taardal.pixelcave.domain.GameObject;
import no.taardal.pixelcave.domain.layer.GameObjectLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameObjectLayerDeserializer implements JsonDeserializer<GameObjectLayer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TileLayerDeserializer.class);

    @Override
    public GameObjectLayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            GameObjectLayer gameObjectLayer = new GameObjectLayer();
            gameObjectLayer.setGameObjects(getGameObjects(jsonObject));
            return gameObjectLayer;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize game object layer.", e);
            return null;
        }
    }

    private List<GameObject> getGameObjects(JsonObject jsonObject) {
        List<GameObject> gameObjects = new ArrayList<>();
        JsonArray jsonArray = jsonObject.get("objects").getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            gameObjects.add(getGameObject(jsonArray.get(i).getAsJsonObject()));
        }
        return gameObjects;
    }

    private GameObject getGameObject(JsonObject jsonObject) {
        GameObject gameObject = new GameObject();
        gameObject.setProperties(getProperties(jsonObject));
        gameObject.setId(jsonObject.get("id").getAsInt());
        gameObject.setName(jsonObject.get("name").getAsString());
        gameObject.setType(jsonObject.get("type").getAsString());
        gameObject.setWidth(jsonObject.get("width").getAsInt());
        gameObject.setHeight(jsonObject.get("height").getAsInt());
        gameObject.setX(jsonObject.get("x").getAsFloat());
        gameObject.setY(jsonObject.get("y").getAsFloat());
        gameObject.setRotation(jsonObject.get("rotation").getAsFloat());
        gameObject.setVisible(jsonObject.get("visible").getAsBoolean());
        return gameObject;
    }

    private Map<String, Object> getProperties(JsonObject jsonObject) {
        Map<String, Object> properties = new HashMap<>();
        JsonObject propertiesJsonObject = jsonObject.get("properties").getAsJsonObject();
        JsonObject propertyTypesJsonObject = jsonObject.get("propertytypes").getAsJsonObject();
        for (Map.Entry<String, JsonElement> typeEntry : propertyTypesJsonObject.entrySet()) {
            String propertyName = typeEntry.getKey();
            String propertyType = typeEntry.getValue().getAsString();
            properties.put(propertyName, getAsType(propertyType, propertiesJsonObject.get(propertyName)));
        }
        return properties;
    }

    private Object getAsType(String type, JsonElement jsonElement) {
        switch (type) {
            case "string":
                return jsonElement.getAsString();
            case "int":
                return jsonElement.getAsInt();
            case "float":
                return jsonElement.getAsFloat();
            case "bool":
                return jsonElement.getAsBoolean();
            default:
                return null;
        }
    }

}
