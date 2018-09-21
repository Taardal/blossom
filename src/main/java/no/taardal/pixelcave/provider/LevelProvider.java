package no.taardal.pixelcave.provider;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.service.ActorService;
import no.taardal.pixelcave.service.AssetService;
import no.taardal.pixelcave.world.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class LevelProvider {

    private GameConfig gameConfig;
    private AssetService assetService;
    private ActorService actorService;

    @Autowired
    public LevelProvider(GameConfig gameConfig, AssetService assetService, ActorService actorService) {
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
        Actor player = null;
        List<Actor> enemies = new ArrayList<>();
        return new Level(world, ribbons, player, enemies);
    }

}
