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
            return new TileSet.Builder()
                    .setFirstGlobalId(jsonObject.get("firstgid").getAsInt())
                    .setName(jsonObject.get("name").getAsString())
                    .setTileWidth(jsonObject.get("tilewidth").getAsInt())
                    .setTileHeight(jsonObject.get("tileheight").getAsInt())
                    .setNumberOfTiles(jsonObject.get("tilecount").getAsInt())
                    .setNumberOfColumns(jsonObject.get("columns").getAsInt())
                    .setMargin(jsonObject.get("margin").getAsInt())
                    .setSpacing(jsonObject.get("spacing").getAsInt())
                    .setImagePath(jsonObject.get("image").getAsString().replaceFirst("../", ""))
                    .setImageWidth(jsonObject.get("imagewidth").getAsInt())
                    .setImageHeight(jsonObject.get("imageheight").getAsInt())
                    .build();
        } catch (JsonParseException e) {
            LOGGER.error("Could not deserialize layer.", e);
            return null;
        }
    }

}
