package no.taardal.blossom.ribbon;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;

import java.util.List;

public class RibbonManager {

    private static final int HORIZONTAL_SPEED = 4;
    private static final int VERTICAL_SPEED = 1;
    private static final double BASE_SPEED_FACTOR = 0.16;
    private static final double SPEED_FACTOR_INCREMENT = 0.19;

    private List<Ribbon> ribbons;

    public RibbonManager(List<Ribbon> ribbons) {
        this.ribbons = setRibbonSpeeds(ribbons);
    }

    public void update(Direction direction) {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).update(direction);
        }
    }

    public void draw(Camera camera) {
        for (int i = 0; i < ribbons.size(); i++) {
            ribbons.get(i).draw(camera);
        }
    }

    private List<Ribbon> setRibbonSpeeds(List<Ribbon> ribbons) {
        double speedFactor = BASE_SPEED_FACTOR;
        for (Ribbon ribbon : ribbons) {
            ribbon.setSpeedX((int) (speedFactor * HORIZONTAL_SPEED));
            ribbon.setSpeedY(VERTICAL_SPEED);
            speedFactor += SPEED_FACTOR_INCREMENT;
        }
        return ribbons;
    }

}
