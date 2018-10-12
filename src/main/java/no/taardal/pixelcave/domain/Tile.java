package no.taardal.pixelcave.domain;

import no.taardal.pixelcave.camera.Camera;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage bufferedImage;
    private boolean slope;
    private Direction direction;

    public Tile(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        direction = Direction.UNKNOWN;
        slope = isSlopeTile();
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    public boolean isSlope() {
        return slope;
    }

    public Direction getDirection() {
        return direction;
    }

    public void draw(int x, int y, Camera camera) {
        camera.drawImage(bufferedImage, x, y);
    }

    private boolean isSlopeTile() {
        int[] rgbPixels = getRGBPixels();
        int leftFloorY = 0;
        int rightFloorY = 0;
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                int pixel = rgbPixels[x + y * getWidth()];
                if (x == 0) {
                    if (isTransparent(pixel)) {
                        leftFloorY = y;
                    }
                }
                if (x == getWidth() - 1) {
                    if (isTransparent(pixel)) {
                        rightFloorY = y;
                    }
                }
            }
        }
        if (leftFloorY != rightFloorY) {
            if (leftFloorY < rightFloorY) {
                direction = Direction.LEFT;
            }
            if (leftFloorY > rightFloorY) {
                direction = Direction.RIGHT;
            }
            return true;
        } else {
            return false;
        }
    }

    private int[] getRGBPixels() {
        int startX = 0;
        int startY = 0;
        int offset = 0;
        int scanSize = getWidth();
        int[] rgbArray = new int[getWidth() * getHeight()];
        return bufferedImage.getRGB(startX, startY, getWidth(), getHeight(), rgbArray, offset, scanSize);
    }

    private boolean isTransparent(int pixel) {
        return (pixel >> 24) == 0x00;
    }

}
