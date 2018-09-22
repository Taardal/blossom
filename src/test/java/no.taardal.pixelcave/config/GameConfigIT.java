package no.taardal.pixelcave.config;

import no.taardal.pixelcave.ITestKit;
import no.taardal.pixelcave.model.gameobject.GameActorType;
import no.taardal.pixelcave.model.animation.AnimationTemplate;
import no.taardal.pixelcave.model.animation.AnimationType;
import no.taardal.pixelcave.model.gameobject.GameActorTemplate;
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
        AnimationTemplate animationTemplate = gameActorTemplate.getAnimationTemplates().get(AnimationType.RUN);
        assertNotNull(animationTemplate);
    }

}
