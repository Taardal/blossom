package no.taardal.pixelcave.service;

import com.google.gson.Gson;
import no.taardal.pixelcave.model.*;
import no.taardal.pixelcave.model.gameobject.GameActorTemplate;
import no.taardal.pixelcave.model.gameobject.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AssetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetService.class);
    private static final String WORLDS_RELATIVE_PATH = "worlds";
    private static final String RIBBONS_RELATIVE_PATH = "ribbons";
    private static final String SPRITE_SHEETS_RELATIVE_PATH = "spritesheets";

    private final ResourceService resourceService;
    private final Gson gson;

    @Autowired
    public AssetService(ResourceService resourceService, Gson gson) {
        this.resourceService = resourceService;
        this.gson = gson;
    }

    public World getWorld(String name) {
        String relativePath = WORLDS_RELATIVE_PATH + "/" + name + ".json";
        World world = gson.fromJson(resourceService.readFile(relativePath), World.class);
        world.setTiles(getTiles(world));
        return world;
    }

    public List<Ribbon> getRibbons(String name) {
        List<Ribbon> ribbons = new ArrayList<>();
        float speed = 1f;
        for (BufferedImage bufferedImage : resourceService.getBufferedImages(RIBBONS_RELATIVE_PATH + "/" + name)) {
            Ribbon ribbon = new Ribbon(bufferedImage);
            ribbon.setSpeedX(speed);
            ribbons.add(ribbon);
            speed += 0.1f;
        }
        return ribbons;
    }

    public SpriteSheet getSpriteSheet(GameObject gameObject, GameActorTemplate gameActorTemplate) {
        return new SpriteSheet.Builder()
                .setBufferedImage(resourceService.getBufferedImage(getSpriteSheetPath(gameObject)))
                .setApproximateSpriteWidth(gameActorTemplate.getApproximateWidth())
                .setApproximateSpriteHeight(gameActorTemplate.getApproximateHeight())
                .build();
    }

    private Map<Integer, Tile> getTiles(World world) {
        Map<Integer, Tile> tiles = new HashMap<>();
        for (TileSet tileSet : world.getTileSets()) {
            int globalId = tileSet.getFirstGlobalId();
            for (Tile tile : getTiles(tileSet)) {
                tiles.put(globalId, tile);
                globalId++;
            }
        }
        return tiles;
    }

    private List<Tile> getTiles(TileSet tileSet) {
        List<Tile> tiles = new ArrayList<>();
        BufferedImage tileSetImage = resourceService.getBufferedImage(tileSet.getImagePath());
        int numberOfTilesX = tileSetImage.getWidth() / tileSet.getTileWidth();
        int numberOfTilesY = tileSetImage.getHeight() / tileSet.getTileHeight();
        for (int y = 0; y < numberOfTilesY; y++) {
            for (int x = 0; x < numberOfTilesX; x++) {
                int subImageX = x * tileSet.getTileWidth();
                int subImageY = y * tileSet.getTileHeight();
                BufferedImage tileImage = tileSetImage.getSubimage(subImageX, subImageY, tileSet.getTileWidth(), tileSet.getTileHeight());
                tiles.add(new Tile(tileImage));
            }
        }
        return tiles;
    }

    private String getSpriteSheetPath(GameObject gameObject) {
        String type = gameObject.getType();
        String theme = (String) gameObject.getProperties().get("theme");
        String gender = (String) gameObject.getProperties().get("gender");
        return SPRITE_SHEETS_RELATIVE_PATH + "/" + type + "/" + type + "-" + gender + "-" + theme + ".png";
    }

}
