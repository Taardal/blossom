package no.taardal.pixelcave.jsondeserializer;

import com.google.gson.*;
import no.taardal.pixelcave.model.layer.TileLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class TileLayerDeserializer implements JsonDeserializer<TileLayer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TileLayerDeserializer.class);

    @Override
    public TileLayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int width = jsonObject.get("width").getAsInt();
            int height = jsonObject.get("height").getAsInt();
            return new TileLayer.Builder()
                    .setTileGrid(getTileGrid(jsonObject, width, height))
                    .setWidth(width)
                    .setHeight(height)
                    .createTileLayer();
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            return null;
        }
    }

    private int[][] getTileGrid(JsonObject jsonObject, int width, int height) {
        int[] tiles = getTiles(jsonObject);
        int[][] tileGrid = new int[width][height];
        for (int x = 0; x < tileGrid.length; x++) {
            for (int y = 0; y < tileGrid[x].length; y++) {
                tileGrid[x][y] = tiles[x + y * tileGrid.length];
            }
        }
        return tileGrid;
    }

    private int[] getTiles(JsonObject jsonObject) {
        JsonArray dataJsonArray = jsonObject.get("data").getAsJsonArray();
        int[] data = new int[dataJsonArray.size()];
        for (int i = 0; i < dataJsonArray.size(); i++) {
            data[i] = dataJsonArray.get(i).getAsInt();
        }
        return data;
    }

}
