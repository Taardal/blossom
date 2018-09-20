package no.taardal.pixelcave.gameframe;

import no.taardal.pixelcave.config.GameConfig;
import no.taardal.pixelcave.keyboard.Keyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class GameFrame extends JFrame {

    @Autowired
    public GameFrame(GameCanvas gameCanvas, Keyboard keyboard, GameConfig gameConfig) throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setTitle(gameConfig.getTitle());
        addKeyListener(keyboard);
        add(gameCanvas);
        pack();
    }
}
