package no.taardal.pixelcave.gameframe;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.state.game.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferStrategy;

@Component
public class GameCanvas extends Canvas {

    static final int SCALE = 3;

    private static final int NUMBER_OF_BUFFERS = 3;

    private Camera camera;

    @Autowired
    public GameCanvas(Camera camera) {
        this.camera = camera;
        setSize();
    }

    public void draw(GameState gameState) {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy != null) {
            camera.clear();
            gameState.draw(camera);
            drawCameraToBuffer(bufferStrategy);
            bufferStrategy.show();
        } else {
            createBufferStrategy(NUMBER_OF_BUFFERS);
        }
    }

    private void setSize() {
        int width = camera.getWidth() * SCALE;
        int height = camera.getHeight() * SCALE;
        setPreferredSize(new Dimension(width, height));
    }

    private void drawCameraToBuffer(BufferStrategy bufferStrategy) {
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(camera.getBufferedImage(), 0, 0, getWidth(), getHeight(), null);
        graphics.dispose();
    }

}
