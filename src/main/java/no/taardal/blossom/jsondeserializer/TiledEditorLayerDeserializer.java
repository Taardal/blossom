package no.taardal.blossom.jsondeserializer;

import com.google.gson.*;
import no.taardal.blossom.gameobject.TiledEditorObject;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.layer.TiledEditorLayerType;
import no.taardal.blossom.order.DrawOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TiledEditorLayerDeserializer implements JsonDeserializer<TiledEditorLayer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiledEditorLayerDeserializer.class);

    @Override
    public TiledEditorLayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            TiledEditorLayer tiledEditorLayer = new TiledEditorLayer();
            tiledEditorLayer.setName(jsonObject.get("name").getAsString());
            tiledEditorLayer.setX(jsonObject.get("x").getAsInt());
            tiledEditorLayer.setY(jsonObject.get("y").getAsInt());
            tiledEditorLayer.setWidth(jsonObject.get("width").getAsInt());
            tiledEditorLayer.setHeight(jsonObject.get("height").getAsInt());
            tiledEditorLayer.setOpacity(jsonObject.get("opacity").getAsInt());
            tiledEditorLayer.setVisible(jsonObject.get("visible").getAsBoolean());
            tiledEditorLayer.setTiledEditorLayerType(getTiledEditorLayerType(jsonObject));
            if (tiledEditorLayer.getTiledEditorLayerType() == TiledEditorLayerType.TILELAYER) {
                tiledEditorLayer.setData(getData(jsonObject));
                tiledEditorLayer.setData2D(getData2D(tiledEditorLayer.getData(), tiledEditorLayer.getWidth(), tiledEditorLayer.getHeight()));
            } else if (tiledEditorLayer.getTiledEditorLayerType() == TiledEditorLayerType.OBJECTGROUP) {
                tiledEditorLayer.setDrawOrder(getDrawOrder(jsonObject));
                tiledEditorLayer.setTiledEditorObjects(getTiledEditorObjects(jsonObject));
            } else {
                LOGGER.warn("Could not determine layer type.");
            }
            LOGGER.info("Deserialized layer [{}].", tiledEditorLayer);
            return tiledEditorLayer;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            throw new RuntimeException(e);
        }
    }

    private TiledEditorLayerType getTiledEditorLayerType(JsonObject jsonObject) {
        String layerType = jsonObject.get("type").getAsString();
        return TiledEditorLayerType.valueOf(layerType.toUpperCase().replaceAll("-", "_"));
    }

    private int[] getData(JsonObject jsonObject) {
        JsonArray dataJsonArray = jsonObject.get("data").getAsJsonArray();
        int[] data = new int[dataJsonArray.size()];
        for (int i = 0; i < dataJsonArray.size(); i++) {
            data[i] = dataJsonArray.get(i).getAsInt();
        }
        return data;
    }

    private int[][] getData2D(int[] data, int width, int height) {
        int[][] data2D = new int[width][height];
        for (int x = 0; x < data2D.length; x++) {
            for (int y = 0; y < data2D[x].length; y++) {
                data2D[x][y] = data[x + y * data2D.length];
            }
        }
        return data2D;
    }

    private DrawOrder getDrawOrder(JsonObject jsonObject) {
        String layerType = jsonObject.get("draworder").getAsString();
        return DrawOrder.valueOf(layerType.toUpperCase().replaceAll("-", "_"));
    }

    private List<TiledEditorObject> getTiledEditorObjects(JsonObject jsonObject) {
        List<TiledEditorObject> tiledEditorObjects = new ArrayList<>();
        JsonArray objectsJsonArray = jsonObject.get("objects").getAsJsonArray();
        for (int i = 0; i < objectsJsonArray.size(); i++) {
            tiledEditorObjects.add(getTiledEditorObject(objectsJsonArray.get(i).getAsJsonObject()));
        }
        return tiledEditorObjects;
    }

    private TiledEditorObject getTiledEditorObject(JsonObject jsonObject) {
        TiledEditorObject tiledEditorObject = new TiledEditorObject(jsonObject.get("id").getAsInt());
        tiledEditorObject.setName(jsonObject.get("name").getAsString());
        tiledEditorObject.setType(jsonObject.get("type").getAsString());
        tiledEditorObject.setWidth(jsonObject.get("width").getAsInt());
        tiledEditorObject.setHeight(jsonObject.get("height").getAsInt());
        tiledEditorObject.setX(jsonObject.get("x").getAsFloat());
        tiledEditorObject.setY(jsonObject.get("y").getAsFloat());
        tiledEditorObject.setRotation(jsonObject.get("rotation").getAsFloat());
        tiledEditorObject.setVisible(jsonObject.get("visible").getAsBoolean());
        return tiledEditorObject;
    }

}
