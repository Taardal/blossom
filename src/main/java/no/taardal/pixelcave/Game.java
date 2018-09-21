package no.taardal.pixelcave;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.gameframe.GameFrame;
import no.taardal.pixelcave.gameloop.GameLoop;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.level.Level;
import no.taardal.pixelcave.state.GameState;
import no.taardal.pixelcave.state.PlayGameState;
import no.taardal.pixelcave.statemachine.StateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@SpringBootApplication
public class Game implements GameLoop.Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private GameFrame gameFrame;
    private GameLoop gameLoop;
    private Keyboard keyboard;
    private Camera camera;
    private List<Level> levels;
    private StateMachine<GameState> gameStateMachine;

    @Autowired
    public Game(GameFrame gameFrame, Keyboard keyboard, Camera camera, List<Level> levels) {
        this();
        this.gameFrame = gameFrame;
        this.keyboard = keyboard;
        this.camera = camera;
        this.levels = levels;
    }

    private Game() {
        gameLoop = new GameLoop(this);
        gameStateMachine = new StateMachine<>();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Game.class).headless(false).build().run(args);
    }

    @PostConstruct
    public synchronized void postConstruct() {
        gameStateMachine.onPushState(new PlayGameState(levels.get(0), gameStateMachine));
        new Thread(gameLoop, "GAME_LOOP").start();
        gameFrame.requestFocus();
        LOGGER.info("Game started...");
    }

    @PreDestroy
    public synchronized void preDestroy() {
        gameLoop.stop();
        LOGGER.info("Game stopped!");
    }

    @Override
    public void onUserInput() {
        gameStateMachine.getCurrentState().userInput(keyboard);
    }

    @Override
    public void onUpdate(float secondsSinceLastUpdate) {
        gameStateMachine.getCurrentState().update(secondsSinceLastUpdate, camera);
    }

    @Override
    public void onDraw() {
        camera.clear();
        gameStateMachine.getCurrentState().draw(camera);
        gameFrame.draw(camera);
    }
}
