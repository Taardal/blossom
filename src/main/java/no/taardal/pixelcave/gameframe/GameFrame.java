package no.taardal.pixelcave.gameframe;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.keyboard.Keyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class GameFrame extends JFrame {

    private GameCanvas gameCanvas;

    @Autowired
    public GameFrame(GameConfig gameConfig, Keyboard keyboard) throws HeadlessException {
        gameCanvas = new GameCanvas(gameConfig);
        add(gameCanvas);
        addKeyListener(keyboard);
        setTitle(gameConfig.getTitle());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }

    public void draw(Camera camera) {
        gameCanvas.draw(camera);
    }
}
