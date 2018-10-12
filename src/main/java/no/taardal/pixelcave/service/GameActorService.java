package no.taardal.pixelcave.service;

import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.domain.Direction;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.domain.Vector2f;
import no.taardal.pixelcave.domain.World;
import no.taardal.pixelcave.animation.Animation;
import no.taardal.pixelcave.domain.AnimationType;
import no.taardal.pixelcave.domain.SpriteSheetCoordinate;
import no.taardal.pixelcave.domain.GameActor;
import no.taardal.pixelcave.domain.GameActorTemplate;
import no.taardal.pixelcave.domain.GameActorType;
import no.taardal.pixelcave.domain.GameObject;
import no.taardal.pixelcave.domain.layer.GameObjectLayer;
import no.taardal.pixelcave.state.GameActorState;
import no.taardal.pixelcave.state.KnightIdleState;
import no.taardal.pixelcave.statemachine.StateChangeListener;
import no.taardal.pixelcave.statemachine.StateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GameActorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameActorService.class);

    private GameConfig gameConfig;
    private AssetService assetService;

    @Autowired
    public GameActorService(GameConfig gameConfig, AssetService assetService) {
        this.gameConfig = gameConfig;
        this.assetService = assetService;
    }

    List<GameActor> getGameActors(World world) {
        GameObjectLayer gameActorLayer = world.getGameObjectLayers().get(gameConfig.getActorLayerName());
        return gameActorLayer.getGameObjects().stream().map(this::getGameActor).collect(Collectors.toList());
    }

    private GameActor getGameActor(GameObject gameObject) {
        GameActorType gameActorType = getActorType(gameObject);
        GameActorTemplate gameActorTemplate = gameConfig.getTemplates().get(gameActorType);
        GameActor gameActor = new GameActor();
        gameActor.setGameActorType(gameActorType);
        gameActor.setAnimations(getAnimations(gameObject, gameActorTemplate));
        gameActor.setMovementStateMachine(getMovementStateMachine(gameActor));
        gameActor.setControllable(gameObject.getName().equalsIgnoreCase("player"));
        gameActor.setVelocity(Vector2f.zero());
        gameActor.setDirection(Direction.UNKNOWN);
        gameActor.setWidth(gameActorTemplate.getApproximateWidth());
        gameActor.setHeight(gameActorTemplate.getApproximateHeight());
        gameActor.setId(gameObject.getId());
        gameActor.setName(gameObject.getName());
        gameActor.setType(gameObject.getType());
        gameActor.setProperties(gameObject.getProperties());
        gameActor.setRotation(gameObject.getRotation());
        gameActor.setVisible(gameObject.isVisible());
        gameActor.setPosition(gameObject.getPosition());
        return gameActor;
    }

    private GameActorType getActorType(GameObject gameObject) {
        return GameActorType.valueOf(gameObject.getType().toUpperCase().replaceAll(" ", "_"));
    }

    private Map<AnimationType, Animation> getAnimations(GameObject gameObject, GameActorTemplate gameActorTemplate) {
        SpriteSheet spriteSheet = assetService.getSpriteSheet(gameObject, gameActorTemplate);
        return gameActorTemplate.getSpriteSheetCoordinates()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    SpriteSheetCoordinate spriteSheetCoordinate = entry.getValue();
                    return new Animation(getSprites(spriteSheetCoordinate, spriteSheet));
                }));
    }

    private List<BufferedImage> getSprites(SpriteSheetCoordinate spriteSheetCoordinate, SpriteSheet spriteSheet) {
        int startY = spriteSheetCoordinate.getY();
        int endY = spriteSheetCoordinate.getY() + spriteSheetCoordinate.getFrames();
        return IntStream.range(startY, endY)
                .boxed()
                .map(y -> spriteSheet.getSprites()[y][spriteSheetCoordinate.getX()])
                .collect(Collectors.toList());
    }

    private StateMachine<GameActorState> getMovementStateMachine(GameActor gameActor) {
        StateMachine<GameActorState> movementStateMachine = new StateMachine<>();
        GameActorState idleState = getIdleState(gameActor, movementStateMachine);
        if (idleState != null) {
            movementStateMachine.onPushState(idleState);
        }
        return movementStateMachine;
    }

    private GameActorState getIdleState(GameActor gameActor, StateChangeListener<GameActorState> stateChangeListener) {
        if (gameActor.getGameActorType() == GameActorType.KNIGHT) {
            return new KnightIdleState(gameActor, stateChangeListener);
        } else {
            return null;
        }
    }

}
