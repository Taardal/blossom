package no.taardal.pixelcave.domain.animation;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.domain.gameobject.GameActor;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.IntStream;

public class Animation {

    private static final int DEFAULT_UPDATES_PER_FRAME = 5;

    private List<BufferedImage> sprites;
    private BufferedImage currentSprite;
    private int frame;
    private int updatesPerFrame;
    private int updatesSinceLastFrame;
    private int width;
    private int height;
    private boolean indefinite;
    private boolean finished;

    public Animation(List<BufferedImage> sprites) {
        this();
        this.sprites = sprites;
        currentSprite = sprites.get(0);
        setSizeFromLargestSprite(sprites);
    }

    private Animation() {
        updatesPerFrame = DEFAULT_UPDATES_PER_FRAME;
        indefinite = true;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFrame() {
        return frame;
    }

    public int getUpdatesPerFrame() {
        return updatesPerFrame;
    }

    public void setUpdatesPerFrame(int updatesPerFrame) {
        this.updatesPerFrame = updatesPerFrame;
    }

    public boolean isIndefinite() {
        return indefinite;
    }

    public void setIndefinite(boolean indefinite) {
        this.indefinite = indefinite;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        if (finished) {
            setIndefinite(false);
        }
    }

    public void update() {
        if (!finished) {
            if (updatesSinceLastFrame >= updatesPerFrame) {
                if (frame > sprites.size() - 1) {
                    if (indefinite) {
                        frame = 0;
                    } else {
                        finished = true;
                    }
                } else {
                    currentSprite = sprites.get(frame);
                    frame++;
                }
                updatesSinceLastFrame = 0;
            }
            updatesSinceLastFrame++;
        }
    }

    public void reset() {
        finished = false;
        currentSprite = sprites.get(0);
        frame = 0;
        updatesSinceLastFrame = 0;
    }

    public void draw(GameActor gameActor, Camera camera) {
        float y = gameActor.getPosition().getY() + gameActor.getHeight() - currentSprite.getHeight();
        float x = gameActor.getPosition().getX();
        camera.drawImage(currentSprite, x, y);
    }

    private void setSizeFromLargestSprite(List<BufferedImage> sprites) {
        IntStream.range(0, sprites.size()).forEach(i -> {
            width = sprites.get(i).getWidth() > width ? sprites.get(i).getWidth() : width;
            height = sprites.get(i).getHeight() > height ? sprites.get(i).getHeight() : height;
        });
    }

}
