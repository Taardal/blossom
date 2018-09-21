package no.taardal.pixelcave.state;

import no.taardal.pixelcave.camera.Camera;
import no.taardal.pixelcave.keyboard.Keyboard;

public interface GameState extends State {

    GameState userInput(Keyboard keyboard);
    GameState update(float secondsSinceLastUpdate, Camera camera);
    void draw(Camera camera);
}
