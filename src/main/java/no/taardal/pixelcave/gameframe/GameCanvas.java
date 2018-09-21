package no.taardal.pixelcave.gameframe;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.config.GameConfig;

import java.awt.*;
import java.awt.image.BufferStrategy;

class GameCanvas extends Canvas {

    private static final int NUMBER_OF_BUFFERS = 3;

    GameCanvas(GameConfig gameConfig) {
        int width = gameConfig.getWidth() * gameConfig.getScale();
        int height = gameConfig.getHeight() * gameConfig.getScale();
        setPreferredSize(new Dimension(width, height));
    }

    public void draw(Camera camera) {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy != null) {
            drawCameraToBuffer(camera, bufferStrategy);
            bufferStrategy.show();
        } else {
            createBufferStrategy(NUMBER_OF_BUFFERS);
        }
    }

    private void drawCameraToBuffer(Camera camera, BufferStrategy bufferStrategy) {
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(camera.getBufferedImage(), 0, 0, getWidth(), getHeight(), null);
        graphics.dispose();
    }

}
