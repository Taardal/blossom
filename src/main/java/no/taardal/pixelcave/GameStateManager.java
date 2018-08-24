package no.taardal.pixelcave;

import no.taardal.pixelcave.game.GameCanvas;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.state.game.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameStateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStateManager.class);

    private GameCanvas gameCanvas;
    private Keyboard keyboard;
    private GameState gameState;

    /*
    Config#levelNameList
    Load level from file system
    Create game state with level
     */

    @Autowired
    public GameStateManager(GameCanvas gameCanvas, Keyboard keyboard) {
        this.gameCanvas = gameCanvas;
        this.keyboard = keyboard;
    }

    public void onHandleInput() {
        /*GameState gameState = this.gameState.handleInput(keyboard);
        if (gameState != null) {
            this.gameState = gameState;
            LOGGER.info("Entered game state [{}]", gameState);
        }*/
    }

    public void onUpdate(float secondsSinceLastUpdate) {
        //gameState.update(secondsSinceLastUpdate);
    }

    public void onDraw() {
        gameCanvas.draw(gameState);
    }
}
