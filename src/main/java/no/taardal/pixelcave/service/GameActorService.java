package no.taardal.pixelcave.service;

import no.taardal.pixelcave.model.gameobject.GameActor;
import no.taardal.pixelcave.model.gameobject.GameActorType;
import no.taardal.pixelcave.model.animation.Animation;
import no.taardal.pixelcave.model.animation.AnimationType;
import no.taardal.pixelcave.model.animation.AnimationTemplate;
import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.model.gameobject.GameObject;
import no.taardal.pixelcave.model.gameobject.GameActorTemplate;
import no.taardal.pixelcave.model.SpriteSheet;
import no.taardal.pixelcave.model.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GameActorService {

    private static final String GAME_ACTOR_LAYER_NAME = "actors";

    private GameConfig gameConfig;
    private AssetService assetService;
    private Map<GameActorType, GameActor> actorSamples;

    @Autowired
    public GameActorService(GameConfig gameConfig, AssetService assetService, Map<GameActorType, GameActor> actorSamples) {
        this.gameConfig = gameConfig;
        this.assetService = assetService;
        this.actorSamples = actorSamples;
    }

    public GameActor getPlayer(World world) {
        return getGameActorObjects(world).stream()
                .filter(gameObject -> gameObject.getName().equalsIgnoreCase("player"))
                .map(this::getGameActor)
                .findFirst()
                .orElse(null);
    }

    public List<GameActor> getEnemies(World world) {
        return getGameActorObjects(world).stream()
                .filter(gameObject -> !isFriendly(gameObject))
                .map(this::getGameActor)
                .collect(Collectors.toList());
    }

    private List<GameObject> getGameActorObjects(World world) {
        return world.getGameObjectLayers().get(GAME_ACTOR_LAYER_NAME).getGameObjects();
    }

    private GameActor getGameActor(GameObject gameObject) {
        GameActorType gameActorType = getActorType(gameObject);
        return actorSamples.get(gameActorType).toBuilder()
                .setAnimations(getAnimations(gameObject, gameActorType))
                .setPosition(gameObject.getPosition())
                .build();
    }

    private GameActorType getActorType(GameObject gameObject) {
        return GameActorType.valueOf(gameObject.getType().toUpperCase().replaceAll(" ", "_"));
    }

    private Map<AnimationType, Animation> getAnimations(GameObject gameObject, GameActorType gameActorType) {
        GameActorTemplate gameActorTemplate = gameConfig.getTemplates().get(gameActorType);
        SpriteSheet spriteSheet = assetService.getSpriteSheet(gameObject, gameActorTemplate);
        return getAnimations(spriteSheet, gameActorTemplate);
    }

    private Map<AnimationType, Animation> getAnimations(SpriteSheet spriteSheet, GameActorTemplate gameActorTemplate) {
        return gameActorTemplate.getAnimationTemplates()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    AnimationType key = entry.getKey();
                    AnimationTemplate animationTemplate = entry.getValue();
                    return new Animation(getSprites(spriteSheet, animationTemplate));
                }));
    }

    private List<BufferedImage> getSprites(SpriteSheet spriteSheet, AnimationTemplate animationTemplate) {
        return IntStream.range(animationTemplate.getY(), animationTemplate.getY() + animationTemplate.getFrames())
                .boxed()
                .map(y -> spriteSheet.getSprites()[y][animationTemplate.getX()])
                .collect(Collectors.toList());
    }

    private boolean isFriendly(GameObject gameObject) {
        String propertyKey = "friendly";
        return gameObject.getProperties().containsKey(propertyKey) && (boolean) gameObject.getProperties().get(propertyKey);
    }
}
