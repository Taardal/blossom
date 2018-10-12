package no.taardal.pixelcave.jsondeserializer;

import com.google.gson.*;
import no.taardal.pixelcave.domain.TileSet;
import no.taardal.pixelcave.domain.World;
import no.taardal.pixelcave.domain.layer.GameObjectLayer;
import no.taardal.pixelcave.domain.layer.Layer;
import no.taardal.pixelcave.domain.LayerType;
import no.taardal.pixelcave.domain.layer.TileLayer;
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
            World world = new World();
            world.setTileLayers(getTileLayers(layers));
            world.setGameObjectLayers(getGameObjectLayers(layers));
            world.setTileSets(getTileSets(jsonObject, jsonDeserializationContext));
            world.setWidth(jsonObject.get("width").getAsInt());
            world.setHeight(jsonObject.get("height").getAsInt());
            world.setNextObjectId(jsonObject.get("nextobjectid").getAsInt());
            world.setTileHeight(jsonObject.get("tileheight").getAsInt());
            world.setTileWidth(jsonObject.get("tilewidth").getAsInt());
            return world;
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
                .filter(layer -> layer.getType() == LayerType.TILE_LAYER)
                .map(layer -> (TileLayer) layer)
                .collect(Collectors.toMap(Layer::getName, layer -> layer));
    }

    private Map<String, GameObjectLayer> getGameObjectLayers(List<Layer> layers) {
        return layers.stream()
                .filter(layer -> layer.getType() == LayerType.GAME_OBJECT_LAYER)
                .map(layer -> (GameObjectLayer) layer)
                .collect(Collectors.toMap(Layer::getName, layer -> layer));
    }

}