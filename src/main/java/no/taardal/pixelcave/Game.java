package no.taardal.pixelcave;

import no.taardal.pixelcave.game.GameCanvas;
import no.taardal.pixelcave.gameloop.GameLoop;
import no.taardal.pixelcave.keyboard.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.*;

@SpringBootApplication
public class Game extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private final GameLoop gameLoop;

    @Autowired
    public Game(GameLoop gameLoop, GameCanvas gameCanvas, Config config, Keyboard keyboard) {
        this.gameLoop = gameLoop;
        add(gameCanvas);
        pack();
        setTitle(config.getTitle());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(keyboard);
    }

    public static void main(String[] args) {
        SpringApplication.run(Game.class, args);
    }

    @PostConstruct
    public synchronized void postConstruct() {
        new Thread(gameLoop, "GAME_LOOP").start();
        LOGGER.info("Game started...");
    }

    @PreDestroy
    public synchronized void preDestroy() {
        gameLoop.stop();
        LOGGER.info("Game stopped!");
    }

}
