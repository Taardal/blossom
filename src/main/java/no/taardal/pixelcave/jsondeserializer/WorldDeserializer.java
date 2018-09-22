package no.taardal.pixelcave.jsondeserializer;

import com.google.gson.*;
import no.taardal.pixelcave.model.TileSet;
import no.taardal.pixelcave.model.World;
import no.taardal.pixelcave.model.layer.GameObjectLayer;
import no.taardal.pixelcave.model.layer.Layer;
import no.taardal.pixelcave.model.layer.TileLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorldDeserializer implements JsonDeserializer<World> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldDeserializer.class);

    @Override
    public World deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            List<Layer> layers = getLayers(jsonObject, jsonDeserializationContext);
            return new World.Builder()
                    .setTileLayers(getTileLayers(layers))
                    .setGameObjectLayers(getGameObjectLayers(layers))
                    .setTileSets(getTileSets(jsonObject, jsonDeserializationContext))
                    .setWidth(jsonObject.get("width").getAsInt())
                    .setHeight(jsonObject.get("height").getAsInt())
                    .setNextObjectId(jsonObject.get("nextobjectid").getAsInt())
                    .setTileHeight(jsonObject.get("tileheight").getAsInt())
                    .setTileWidth(jsonObject.get("tilewidth").getAsInt())
                    .createWorld();
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize world.", e);
            return null;
        }
    }

    private List<TileSet> getTileSets(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        JsonArray tileSetsJsonArray = jsonObject.get("tilesets").getAsJsonArray();
        return Arrays.asList(jsonDeserializationContext.deserialize(tileSetsJsonArray, TileSet[].class));
    }

    private List<Layer> getLayers(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        JsonArray jsonArray = jsonObject.get("layers").getAsJsonArray();
        return Arrays.asList(jsonDeserializationContext.deserialize(jsonArray, Layer[].class));
    }

    private Map<String, TileLayer> getTileLayers(List<Layer> layers) {
        return layers.stream()
                .filter(layer -> layer.getType() == Layer.Type.TILE_LAYER)
                .map(layer -> (TileLayer) layer)
                .collect(Collectors.toMap(Layer::getName, layer -> layer));
    }

    private Map<String, GameObjectLayer> getGameObjectLayers(List<Layer> layers) {
        return layers.stream()
                .filter(layer -> layer.getType() == Layer.Type.GAME_OBJECT_LAYER)
                .map(layer -> (GameObjectLayer) layer)
                .collect(Collectors.toMap(Layer::getName, layer -> layer));
    }

}