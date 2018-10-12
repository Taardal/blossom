package no.taardal.pixelcave.jsondeserializer;

import com.google.gson.*;
import no.taardal.pixelcave.domain.TileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class TileSetDeserializer implements JsonDeserializer<TileSet> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TileSetDeserializer.class);

    @Override
    public TileSet deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            TileSet tileSet = new TileSet();
            tileSet.setFirstGlobalId(jsonObject.get("firstgid").getAsInt());
            tileSet.setName(jsonObject.get("name").getAsString());
            tileSet.setTileWidth(jsonObject.get("tilewidth").getAsInt());
            tileSet.setTileHeight(jsonObject.get("tileheight").getAsInt());
            tileSet.setNumberOfTiles(jsonObject.get("tilecount").getAsInt());
            tileSet.setNumberOfColumns(jsonObject.get("columns").getAsInt());
            tileSet.setMargin(jsonObject.get("margin").getAsInt());
            tileSet.setSpacing(jsonObject.get("spacing").getAsInt());
            tileSet.setImagePath(jsonObject.get("image").getAsString().replaceFirst("../", ""));
            tileSet.setImageWidth(jsonObject.get("imagewidth").getAsInt());
            tileSet.setImageHeight(jsonObject.get("imageheight").getAsInt());
            return tileSet;
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            return null;
        }
    }

}
