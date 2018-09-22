package no.taardal.pixelcave.service;

import no.taardal.pixelcave.ITestKit;
import no.taardal.pixelcave.model.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AssetServiceIT extends ITestKit {

    private static final String SAMPLE_WORLD_NAME = "pixelcave";

    @Autowired
    private AssetService assetService;

    @Test
    public void loadsWorldByName() {
        World world = assetService.getWorld(SAMPLE_WORLD_NAME);

        assertNotNull(world);
        assertFalse(world.getTiles().isEmpty());
        assertFalse(world.getTileSets().isEmpty());
        assertFalse(world.getTileLayers().isEmpty());
        assertFalse(world.getGameObjectLayers().isEmpty());
    }

    @Test
    public void loadsRibbonsByName() {
        assertFalse(assetService.getRibbons(SAMPLE_WORLD_NAME).isEmpty());
    }

    @Test
    public void loadsSpriteSheetByNameTemplate() {

    }
}
