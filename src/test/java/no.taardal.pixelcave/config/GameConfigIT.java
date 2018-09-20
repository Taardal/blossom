package no.taardal.pixelcave.config;

import no.taardal.pixelcave.ITestKit;
import no.taardal.pixelcave.actor.ActorType;
import no.taardal.pixelcave.animation.SpriteSheetCoordinate;
import no.taardal.pixelcave.animation.AnimationType;
import no.taardal.pixelcave.sprite.ActorTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GameConfigIT extends ITestKit {

    @Autowired
    private GameConfig gameConfig;

    @Test
    public void readSpriteConfig() {
        assertNotNull(gameConfig);

        ActorTemplate actorTemplate = gameConfig.getTemplates().get(ActorType.KNIGHT);
        SpriteSheetCoordinate spriteSheetCoordinate = actorTemplate.getSpriteSheetCoordinates().get(AnimationType.RUN);
        assertNotNull(spriteSheetCoordinate);
    }

}
