package no.taardal.pixelcave.provider;

import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.model.Ribbon;
import no.taardal.pixelcave.model.World;
import no.taardal.pixelcave.model.gameobject.GameActor;
import no.taardal.pixelcave.service.AssetService;
import no.taardal.pixelcave.service.GameActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class LevelProvider {

    private GameConfig gameConfig;
    private AssetService assetService;
    private GameActorService gameActorService;

    @Autowired
    public LevelProvider(GameConfig gameConfig, AssetService assetService, GameActorService gameActorService) {
        this.gameConfig = gameConfig;
        this.assetService = assetService;
        this.gameActorService = gameActorService;
    }

    @Bean
    public List<Level> provideLevels() {
        return gameConfig.getLevels().stream().map(this::getLevel).collect(Collectors.toList());
    }

    private Level getLevel(String levelName) {
        World world = assetService.getWorld(levelName);
        List<Ribbon> ribbons = assetService.getRibbons(levelName);
        GameActor player = gameActorService.getPlayer(world);
        List<GameActor> enemies = gameActorService.getEnemies(world);
        return new Level(world, ribbons, player, enemies);
    }

}
