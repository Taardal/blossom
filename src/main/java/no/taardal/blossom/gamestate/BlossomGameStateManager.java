package no.taardal.blossom.gamestate;

import com.google.inject.Inject;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.level.Level;
import no.taardal.blossom.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlossomGameStateManager implements GameStateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlossomGameStateManager.class);

    private GameState gameState;

    @Inject
    public BlossomGameStateManager(Service<Level> levelService) {
        Level level = levelService.getLevel("testmap.json");
        gameState = new PlayGameState(level);
    }

    @Override
    public void update(Keyboard keyboard) {
        GameState gameState = this.gameState.update(keyboard);
        if (gameState != null) {
            LOGGER.debug("Changing game state to [{}]", gameState);
            this.gameState = gameState;
        }
    }

    @Override
    public void draw(Camera camera) {
        gameState.draw(camera);
    }

}
