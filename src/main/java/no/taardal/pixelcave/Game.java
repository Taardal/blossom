package no.taardal.pixelcave;

import no.taardal.pixelcave.gameframe.GameFrame;
import no.taardal.pixelcave.gameloop.GameLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class Game {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private final GameLoop gameLoop;
    private final GameFrame gameFrame;

    @Autowired
    public Game(GameLoop gameLoop, GameFrame gameFrame) {
        this.gameLoop = gameLoop;
        this.gameFrame = gameFrame;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Game.class).headless(false).build().run(args);
    }

    @PostConstruct
    public synchronized void postConstruct() {
        new Thread(gameLoop, "GAME_LOOP").start();
        gameFrame.requestFocus();
        LOGGER.info("Game started...");
    }

    @PreDestroy
    public synchronized void preDestroy() {
        gameLoop.stop();
        LOGGER.info("Game stopped!");
    }

}
