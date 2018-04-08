package no.taardal.pixelcave.camera;

import no.taardal.pixelcave.actor.Player;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.game.Game;
import no.taardal.pixelcave.sprite.Sprite;
import no.taardal.pixelcave.vector.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Camera {

    private static final Logger LOGGER = LoggerFactory.getLogger(Camera.class);

    private BufferedImage bufferedImage;
    private Direction direction;
    private int width;
    private int height;
    private int x;
    private int y;
    private int left;
    private int right;
    private int top;
    private int bottom;
    private float previousPlayerX;
    private boolean centerOnPlayerRequired;
    private int[] pixels;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        direction = Direction.NO_DIRECTION;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        centerOnPlayerRequired = true;

        left = (int) (width * (30 / 100.0f));
        right = (int) (width * (70 / 100.0f));
        top = (int) (height * (30 / 100.0f));
        bottom = (int) (height * (70 / 100.0f));

        pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void update(Player player) {
        if (centerOnPlayerRequired) {
            x = ((int) player.getPosition().getX()) - Game.GAME_WIDTH / 2;
            centerOnPlayerRequired = false;
        }
        float playerX = player.getPosition().getX();
        float playerXInCamera = playerX - x;
        if (playerXInCamera < left || (playerXInCamera + player.getWidth()) > right) {
            if (playerXInCamera < left) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }
            x += (int) playerX - (int) previousPlayerX;
        } else {
            direction = Direction.NO_DIRECTION;
        }
        previousPlayerX = playerX;
    }

    public void drawSprite(Sprite sprite, Vector2f position) {
        int cameraX = ((int) position.getX()) - x;
        int cameraY = ((int) position.getY()) - y;
        for (int y = 0; y < sprite.getHeight(); y++) {
            int absoluteY = y + cameraY;
            for (int x = 0; x < sprite.getWidth(); x++) {
                int absoluteX = x + cameraX;
                if (absoluteX < -sprite.getWidth() || absoluteX >= width || absoluteY < -sprite.getHeight() || absoluteY >= height) {
                    break;
                }
                if (absoluteX < 0) {
                    absoluteX = 0;
                }
                if (absoluteY < 0) {
                    absoluteY = 0;
                }
                int spritePixel = sprite.getPixels()[x + y * sprite.getWidth()];
                pixels[absoluteX + absoluteY * width] = spritePixel;
            }
        }
    }

    public void drawImage(BufferedImage bufferedImage, float x, float y, boolean flip) {
        if (flip) {
            drawImageFlippedHorizontally(bufferedImage, x, y);
        } else {
            drawImage(bufferedImage, x, y);
        }
    }

    public void drawImage(BufferedImage bufferedImage, float x, float y) {

    }

    public void drawImage(BufferedImage bufferedImage, int destinationX1, int destinationX2, int destinationY1, int destinationY2, int sourceX1, int sourceX2, int sourceY1, int sourceY2) {

    }

    public void drawRectangle(float x, float y, int width, int height, Color color) {

    }

    public void drawString(String text, int x, int y, Font font, Color color) {

    }

    public void drawCircle(int x, int y, int diameter, Color color) {

    }

    private void drawImageFlippedHorizontally(BufferedImage bufferedImage, float x, float y) {
        x -= this.x;
        y -= this.y;
        int sourceX1 = 0;
        int sourceX2 = bufferedImage.getWidth();
        int sourceY1 = 0;
        int sourceY2 = bufferedImage.getHeight();
        int destinationX1 = (int) x;
        int destinationX2 = (int) x + bufferedImage.getWidth();
        int destinationY1 = (int) y;
        int destinationY2 = (int) y + bufferedImage.getHeight();
        drawImage(
                bufferedImage,
                destinationX1,
                destinationX2,
                destinationY1,
                destinationY2,
                sourceX2,
                sourceX1,
                sourceY1,
                sourceY2
        );
    }

}
