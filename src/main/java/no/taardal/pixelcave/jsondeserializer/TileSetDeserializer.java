package no.taardal.pixelcave.jsondeserializer;

import com.google.gson.*;
import no.taardal.pixelcave.service.ResourceService;
import no.taardal.pixelcave.tile.Tile;
import no.taardal.pixelcave.tile.TileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.util.*;

public class TileSetDeserializer implements JsonDeserializer<TileSet> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TileSetDeserializer.class);

    private ResourceService resourceService;
    private JsonParser jsonParser;

    public TileSetDeserializer(ResourceService resourceService) {
        this.resourceService = resourceService;
        jsonParser = new JsonParser();
    }

    @Override
    public TileSet deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject tileSetReferenceJsonObject = jsonElement.getAsJsonObject();
            String tileSetFilePath = tileSetReferenceJsonObject.get("source").getAsString().replaceFirst("../", "");
            TileSet tileSet = getTileSet(tileSetFilePath);
            tileSet.setFirstGlobalId(tileSetReferenceJsonObject.get("firstgid").getAsInt());
            return tileSet;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            return null;
        }
    }

    private TileSet getTileSet(String path) {
        JsonObject jsonObject = jsonParser.parse(resourceService.readFile(path)).getAsJsonObject();

        TileSet tileSet = new TileSet.Builder()
                .setTileWidth(jsonObject.get("tilewidth").getAsInt())
                .setTileHeight(jsonObject.get("tileheight").getAsInt())
                .setName(jsonObject.get("name").getAsString())
                .setNumberOfTiles(jsonObject.get("tilecount").getAsInt())
                .setNumberOfColumns(jsonObject.get("columns").getAsInt())
                .setMargin(jsonObject.get("margin").getAsInt())
                .setSpacing(jsonObject.get("spacing").getAsInt())
                .createTileSet();

        return updateTileSetWithTiles(tileSet, jsonObject, path);
    }

    private TileSet updateTileSetWithTiles(TileSet tileSet, JsonObject jsonObject, String tileSetPath) {
        JsonElement imageJsonElement = jsonObject.get("image");
        if (imageJsonElement != null && !imageJsonElement.isJsonNull()) {
            tileSet.setImagePath(tileSetPath.substring(0, tileSetPath.lastIndexOf("/")) + "/" + imageJsonElement.getAsString());
            tileSet.setImageWidth(jsonObject.get("imagewidth").getAsInt());
            tileSet.setImageHeight(jsonObject.get("imageheight").getAsInt());
            tileSet.setTiles(getTilesFromImage(tileSet.getImagePath(), tileSet.getTileWidth(), tileSet.getTileHeight()));
        } else {
            tileSet.setTiles(getTilesFromJson(jsonObject));
        }
        return tileSet;
    }

    private List<Tile> getTilesFromImage(String imagePath, int tileWidth, int tileHeight) {
        List<Tile> tiles = new ArrayList<>();
        BufferedImage tileSetImage = resourceService.getBufferedImage(imagePath);
        int numberOfTilesY = tileSetImage.getHeight() / tileHeight;
        int numberOfTilesX = tileSetImage.getWidth() / tileWidth;
        for (int y = 0; y < numberOfTilesY; y++) {
            for (int x = 0; x < numberOfTilesX; x++) {
                int subImageX = x * tileWidth;
                int subImageY = y * tileHeight;
                BufferedImage tileImage = tileSetImage.getSubimage(subImageX, subImageY, tileWidth, tileHeight);
                tiles.add(new Tile(tileImage));
            }
        }
        return tiles;
    }

    private List<Tile> getTilesFromJson(JsonObject jsonObject) {
        Map<Integer, Tile> tiles = new TreeMap<>();
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.get("tiles").getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            Integer id = Integer.valueOf(entry.getKey());
            tiles.put(id, new Tile(getTileImage(entry.getValue())));
        }
        return new ArrayList<>(tiles.values());
    }

    private BufferedImage getTileImage(JsonElement jsonElement) {
        JsonElement imageJsonElement = jsonElement.getAsJsonObject().get("image");
        String imagePath = imageJsonElement.getAsString().replaceFirst("../", "");
        return resourceService.getBufferedImage(imagePath);
    }

}
