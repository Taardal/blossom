package no.taardal.pixelcave.gameloop;

import no.taardal.pixelcave.statemachine.GameStateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameLoop implements Runnable {

    static final int UPDATE_DELTA_THRESHOLD = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);
    private static final int ONE_SECOND_IN_MILLISECONDS = 1000;
    private static final int ONE_SECOND_IN_NANOSECONDS = 1000000000;
    private static final float UPDATES_PER_SECOND_TARGET = 60;
    private static final float NANOSECONDS_PER_UPDATE = ONE_SECOND_IN_NANOSECONDS / UPDATES_PER_SECOND_TARGET;

    private static long lastPassMillis = 0;
    private static long lastPassNano = 0;

    private GameStateManager gameStateManager;
    private boolean running;
    private int frames;
    private int updates;
    private float delta;
    private float nanosecondsSinceLastUpdate;

    @Autowired
    public GameLoop(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            loop();
        }
    }

    public void stop() {
        running = false;
    }

    boolean isRunning() {
        return running;
    }

    void setDelta(float delta) {
        this.delta = delta;
    }

    void loop() {
        if (lastPassMillis == 0) {
            lastPassMillis = System.currentTimeMillis();
        }
        if (lastPassNano == 0) {
            lastPassNano = System.nanoTime();
        }
        long currentTimeMillis = System.currentTimeMillis();
        long currentTimeNano = System.nanoTime();
        long nanosecondsSinceLastPass = currentTimeNano - lastPassNano;
        nanosecondsSinceLastUpdate += nanosecondsSinceLastPass;
        delta += nanosecondsSinceLastPass / NANOSECONDS_PER_UPDATE;
        lastPassNano = currentTimeNano;
        if (delta >= UPDATE_DELTA_THRESHOLD) {
            gameStateManager.onUserInput();
            gameStateManager.onUpdate(nanosecondsSinceLastUpdate / ONE_SECOND_IN_NANOSECONDS);
            updates++;
            delta--;
            nanosecondsSinceLastUpdate = 0;
        }
        gameStateManager.onDraw();
        frames++;
        if (currentTimeMillis - lastPassMillis > ONE_SECOND_IN_MILLISECONDS) {
            lastPassMillis += ONE_SECOND_IN_MILLISECONDS;
            LOGGER.info(updates + " ups, " + frames + " fps");
            updates = 0;
            frames = 0;
        }
    }
}