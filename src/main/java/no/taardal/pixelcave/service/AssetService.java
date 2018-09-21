package no.taardal.pixelcave.service;

import com.google.gson.Gson;
import no.taardal.pixelcave.gameobject.GameObject;
import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.sprite.ActorTemplate;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
        String relativePath = WORLDS_RELATIVE_PATH + "/world_" + name + ".json";
        return gson.fromJson(resourceService.readFile(relativePath), World.class);
    }

    public List<Ribbon> getRibbons(String name) {
        List<Ribbon> ribbons = new ArrayList<>();
        float speed = 1f;
        for (BufferedImage bufferedImage : resourceService.getBufferedImages(RIBBONS_RELATIVE_PATH)) {
            Ribbon ribbon = new Ribbon(bufferedImage);
            ribbon.setSpeedX(speed);
            ribbons.add(ribbon);
            speed += 0.1f;
        }
        return ribbons;
    }

    public SpriteSheet getSpriteSheet(GameObject gameObject, ActorTemplate actorTemplate) {
        return new SpriteSheet.Builder()
                .setBufferedImage(resourceService.getBufferedImage(getSpriteSheetPath(gameObject)))
                .setApproximateSpriteWidth(actorTemplate.getApproximateWidth())
                .setApproximateSpriteHeight(actorTemplate.getApproximateHeight())
                .build();
    }

    private String getSpriteSheetPath(GameObject gameObject) {
        String name = gameObject.getName();
        String theme = (String) gameObject.getProperties().get("theme");
        String gender = (String) gameObject.getProperties().get("gender");
        return SPRITE_SHEETS_RELATIVE_PATH + "/" + name + "/" + name + "-" + gender + "-" + theme + ".png";
    }

}
