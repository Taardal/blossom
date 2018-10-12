package no.taardal.pixelcave.service;

import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.domain.World;
import no.taardal.pixelcave.domain.GameActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LevelService {

    private GameConfig gameConfig;
    private AssetService assetService;
    private GameActorService gameActorService;

    @Autowired
    public LevelService(GameConfig gameConfig, AssetService assetService, GameActorService gameActorService) {
        this.gameConfig = gameConfig;
        this.assetService = assetService;
        this.gameActorService = gameActorService;
    }

    public List<Level> getLevels() {
        return gameConfig.getLevels().stream().map(this::getLevel).collect(Collectors.toList());
    }

    private Level getLevel(String levelName) {
        World world = assetService.getWorld(levelName);
        List<Ribbon> ribbons = assetService.getRibbons(levelName);
        List<GameActor> gameActors = gameActorService.getGameActors(world);
        return new Level(world, ribbons, gameActors);
    }

}
