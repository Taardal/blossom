package no.taardal.pixelcave.jsondeserializer;

import com.google.gson.*;
import no.taardal.pixelcave.domain.layer.GameObjectLayer;
import no.taardal.pixelcave.domain.layer.Layer;
import no.taardal.pixelcave.domain.layer.TileLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class LayerDeserializer implements JsonDeserializer<Layer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LayerDeserializer.class);

    @Override
    public Layer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Layer layer = getLayer(jsonObject, jsonDeserializationContext);
            layer.setName(jsonObject.get("name").getAsString());
            layer.setX(jsonObject.get("x").getAsInt());
            layer.setY(jsonObject.get("y").getAsInt());
            layer.setVisible(jsonObject.get("visible").getAsBoolean());
            return layer;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            return null;
        }
    }

    private Layer getLayer(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        String layerType = jsonObject.get("type").getAsString();
        if (layerType.equals("objectgroup")) {
            return (GameObjectLayer) jsonDeserializationContext.deserialize(jsonObject, GameObjectLayer.class);
        } else {
            return (TileLayer) jsonDeserializationContext.deserialize(jsonObject, TileLayer.class);
        }
    }

}