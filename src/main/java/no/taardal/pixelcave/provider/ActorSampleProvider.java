package no.taardal.pixelcave.provider;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.actor.ActorType;
import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.sprite.ActorTemplate;
import no.taardal.pixelcave.vector.Vector2f;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ActorSampleProvider {

    private GameConfig gameConfig;

    @Autowired
    public ActorSampleProvider(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    @Bean
    public Map<ActorType, Actor> provideActorSamples() {
        return gameConfig.getTemplates().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            ActorTemplate actorTemplate = entry.getValue();
            return new Actor.Builder()
                    .setPosition(new Vector2f(0, 0))
                    .setVelocity(new Vector2f(0, 0))
                    .setDirection(Direction.LEFT)
                    .setWidth(actorTemplate.getApproximateWidth())
                    .setHeight(actorTemplate.getApproximateHeight())
                    .build();
        }));
    }

}
