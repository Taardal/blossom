package no.taardal.pixelcave.state;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.statemachine.StateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayGameState implements GameState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGameState.class);

    private Level level;
    private StateListener<GameState> gameStateListener;

    public PlayGameState(Level level, StateListener<GameState> gameStateListener) {
        this.level = level;
        this.gameStateListener = gameStateListener;
    }

    @Override
    public GameState userInput(Keyboard keyboard) {
        return null;
    }

    @Override
    public GameState update(float secondsSinceLastUpdate, Camera camera) {
        level.updateRibbons(camera);
        return null;
    }

    @Override
    public void draw(Camera camera) {
        level.drawRibbons(camera);
        level.drawTiles(camera);
        level.drawGameActors(camera);
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void onExit() {

    }
}
