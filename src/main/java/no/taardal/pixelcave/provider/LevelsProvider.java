package no.taardal.pixelcave.provider;

import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.service.ActorService;
import no.taardal.pixelcave.service.AssetService;
import no.taardal.pixelcave.world.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class LevelsProvider {

    private final GameConfig gameConfig;
    private final AssetService assetService;
    private final ActorService actorService;

    @Autowired
    public LevelsProvider(GameConfig gameConfig, AssetService assetService, ActorService actorService) {
        this.gameConfig = gameConfig;
        this.assetService = assetService;
        this.actorService = actorService;
    }

    @Bean
    public List<Level> provideLevels() {
        return gameConfig.getLevels().stream().map(this::getLevel).collect(Collectors.toList());
    }

    private Level getLevel(String levelName) {
        World world = assetService.getWorld(levelName);
        List<Ribbon> ribbons = assetService.getRibbons(levelName);
        return new Level(world, ribbons, actorService);
    }

}
