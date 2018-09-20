package no.taardal.pixelcave.statemachine;

import no.taardal.pixelcave.gameframe.GameCanvas;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.state.game.GameState;
import no.taardal.pixelcave.state.game.PlayGameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameStateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStateManager.class);

    private final GameCanvas gameCanvas;
    private final Keyboard keyboard;
    private final List<Level> levels;

    private GameState gameState;

    @Autowired
    public GameStateManager(GameCanvas gameCanvas, Keyboard keyboard, List<Level> levels) {
        this.gameCanvas = gameCanvas;
        this.keyboard = keyboard;
        this.levels = levels;
        gameState = new PlayGameState(levels.get(0));

    }

    public void onUserInput() {
        /*GameState gameState = this.gameState.handleInput(keyboard);
        if (gameState != null) {
            this.gameState = gameState;
            LOGGER.info("Entered gameframe state [{}]", gameState);
        }*/
    }

    public void onUpdate(float secondsSinceLastUpdate) {
        //gameState.update(secondsSinceLastUpdate);
    }

    public void onDraw() {
        gameCanvas.draw(gameState);
    }

    void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
