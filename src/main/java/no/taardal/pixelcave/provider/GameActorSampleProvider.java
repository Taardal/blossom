package no.taardal.pixelcave.provider;

import no.taardal.pixelcave.model.gameobject.GameActor;
import no.taardal.pixelcave.model.gameobject.GameActorType;
import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.model.Direction;
import no.taardal.pixelcave.model.gameobject.GameActorTemplate;
import no.taardal.pixelcave.model.Vector2f;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class GameActorSampleProvider {

    private GameConfig gameConfig;

    @Autowired
    public GameActorSampleProvider(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    @Bean
    public Map<GameActorType, GameActor> provideGameActorSamples() {
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
