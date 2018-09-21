package no.taardal.pixelcave.gameloop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLoop implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);
    private static final int UPDATE_DELTA_THRESHOLD = 1;
    private static final int ONE_SECOND_IN_MILLISECONDS = 1000;
    private static final int ONE_SECOND_IN_NANOSECONDS = 1000000000;
    private static final float UPDATES_PER_SECOND_TARGET = 60;
    private static final float NANOSECONDS_PER_UPDATE = ONE_SECOND_IN_NANOSECONDS / UPDATES_PER_SECOND_TARGET;

    private Listener listener;
    private int frames;
    private int updates;
    private float delta;
    private float nanosecondsSinceLastUpdate;
    private boolean running;

    public GameLoop(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        running = true;
        long lastPassNano = System.nanoTime();
        long lastPassMillis = System.currentTimeMillis();
        while (running) {
            long currentTimeNano = System.nanoTime();
            long currentTimeMillis = System.currentTimeMillis();
            long nanosecondsSinceLastPass = currentTimeNano - lastPassNano;
            nanosecondsSinceLastUpdate += nanosecondsSinceLastPass;
            delta += nanosecondsSinceLastPass / NANOSECONDS_PER_UPDATE;
            lastPassNano = currentTimeNano;
            if (delta >= UPDATE_DELTA_THRESHOLD) {
                listener.onUserInput();
                listener.onUpdate(nanosecondsSinceLastUpdate / ONE_SECOND_IN_NANOSECONDS);
                updates++;
                delta--;
                nanosecondsSinceLastUpdate = 0;
            }
            listener.onDraw();
            frames++;
            if (currentTimeMillis - lastPassMillis > ONE_SECOND_IN_MILLISECONDS) {
                lastPassMillis += ONE_SECOND_IN_MILLISECONDS;
                LOGGER.info(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }

    public void stop() {
        running = false;
    }

    public interface Listener {

        void onUserInput();
        void onUpdate(float secondsSinceLastUpdate);
        void onDraw();

    }
}