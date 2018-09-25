package no.taardal.pixelcave.service;

import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.model.Direction;
import no.taardal.pixelcave.model.Vector2f;
import no.taardal.pixelcave.model.gameobject.GameActor;
import no.taardal.pixelcave.model.gameobject.GameActorTemplate;
import no.taardal.pixelcave.model.gameobject.GameActorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GameActorSampleService {

    private GameConfig gameConfig;

    @Autowired
    public GameActorSampleService(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    Map<GameActorType, GameActor> getSamples() {
        return gameConfig.getTemplates()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> getGameActorSample(entry.getValue())));
    }

    private GameActor getGameActorSample(GameActorTemplate gameActorTemplate) {
        return new GameActor.Builder()
                .setPosition(new Vector2f(0, 0))
                .setVelocity(new Vector2f(0, 0))
                .setDirection(Direction.LEFT)
                .setWidth(gameActorTemplate.getApproximateWidth())
                .setHeight(gameActorTemplate.getApproximateHeight())
                .build();
    }

}
