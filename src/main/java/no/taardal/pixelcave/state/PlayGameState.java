package no.taardal.pixelcave.state;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.statemachine.StateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayGameState implements GameState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGameState.class);

    private Level level;
    private StateChangeListener<GameState> stateChangeListener;

    public PlayGameState(Level level, StateChangeListener<GameState> stateChangeListener) {
        this.level = level;
        this.stateChangeListener = stateChangeListener;
    }

    @Override
    public GameState handleInput(Keyboard keyboard) {
        level.handleInput(keyboard);
        return null;
    }

    @Override
    public GameState update(float secondsSinceLastUpdate, Camera camera) {
        level.updateRibbons(camera);
        level.updateGameActors(secondsSinceLastUpdate);
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
