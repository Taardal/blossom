package no.taardal.pixelcave.service;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.actor.ActorType;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.animation.AnimationType;
import no.taardal.pixelcave.animation.SpriteSheetCoordinate;
import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.gameobject.GameObject;
import no.taardal.pixelcave.layer.GameObjectLayer;
import no.taardal.pixelcave.sprite.ActorTemplate;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.world.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ActorService {

    private final GameConfig gameConfig;
    private final AssetService assetService;
    private final Map<ActorType, Actor> actorSamples;

    @Autowired
    public ActorService(GameConfig gameConfig, AssetService assetService, Map<ActorType, Actor> actorSamples) {
        this.gameConfig = gameConfig;
        this.assetService = assetService;
        this.actorSamples = actorSamples;
    }

    public List<Actor> getEnemies(World world) {
        GameObjectLayer actorLayer = world.getGameObjectLayers().get("actor_layer");
        return getEnemies(actorLayer.getGameObjects());
    }

    private List<Actor> getEnemies(List<GameObject> gameObjects) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.getType().equals("enemy"))
                .map(this::getActor)
                .collect(Collectors.toList());
    }

    private Actor getActor(GameObject gameObject) {
        ActorType actorType = getActorType(gameObject);
        return actorSamples.get(actorType).toBuilder().setAnimations(getAnimations(gameObject, actorType)).build();
    }

    private ActorType getActorType(GameObject gameObject) {
        return ActorType.valueOf(gameObject.getName().toUpperCase().replaceAll(" ", "_"));
    }

    private Map<AnimationType, Animation> getAnimations(GameObject gameObject, ActorType actorType) {
        ActorTemplate actorTemplate = gameConfig.getTemplates().get(actorType);
        SpriteSheet spriteSheet = assetService.getSpriteSheet(gameObject, actorTemplate);
        return getAnimations(spriteSheet, actorTemplate);
    }

    private Map<AnimationType, Animation> getAnimations(SpriteSheet spriteSheet, ActorTemplate actorTemplate) {
        return actorTemplate.getSpriteSheetCoordinates()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    SpriteSheetCoordinate spriteSheetCoordinate = entry.getValue();
                    return new Animation(getSprites(spriteSheet, spriteSheetCoordinate));
                }));
    }

    private List<BufferedImage> getSprites(SpriteSheet spriteSheet, SpriteSheetCoordinate spriteSheetCoordinate) {
        return IntStream.range(spriteSheetCoordinate.getY(), spriteSheetCoordinate.getFrames())
                .boxed()
                .map(y -> spriteSheet.getSprites()[y][spriteSheetCoordinate.getX()])
                .collect(Collectors.toList());
    }
}
