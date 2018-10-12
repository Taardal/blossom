package no.taardal.pixelcave.config;

import no.taardal.pixelcave.ITestKit;
import no.taardal.pixelcave.domain.GameActorType;
import no.taardal.pixelcave.domain.SpriteSheetCoordinate;
import no.taardal.pixelcave.domain.AnimationType;
import no.taardal.pixelcave.domain.GameActorTemplate;
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

        GameActorTemplate gameActorTemplate = gameConfig.getTemplates().get(GameActorType.KNIGHT);
        SpriteSheetCoordinate spriteSheetCoordinate = gameActorTemplate.getSpriteSheetCoordinates().get(AnimationType.RUN);
        assertNotNull(spriteSheetCoordinate);
    }

}
