package no.taardal.pixelcave.state;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.domain.World;
import no.taardal.pixelcave.domain.animation.Animation;
import no.taardal.pixelcave.keyboard.Keyboard;

public interface GameActorState extends State {

    Animation getAnimation();

    void handleInput(Keyboard keyboard);

    void update(World world, float secondsSinceLastUpdate);

    void draw(Camera camera);

}
