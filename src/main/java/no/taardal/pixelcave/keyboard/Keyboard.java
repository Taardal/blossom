package no.taardal.pixelcave.keyboard;

import no.taardal.pixelcave.model.Key;
import no.taardal.pixelcave.model.KeyBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class Keyboard implements KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Keyboard.class);
    private static final int MAX_KEY_CODE = 100;

    private static boolean[] keyStates = new boolean[MAX_KEY_CODE];
    private static boolean[] previousKeyStates = new boolean[MAX_KEY_CODE];

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        LOGGER.info(keyEvent.paramString());
        if (keyEvent.getKeyCode() < MAX_KEY_CODE) {
            keyStates[keyEvent.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() < MAX_KEY_CODE) {
            LOGGER.info(keyEvent.paramString());
            keyStates[keyEvent.getKeyCode()] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    public void update() {
        setCurrentKeyStatesAsPreviousKeyStates();
    }

    public boolean isPressed(KeyBinding keyBinding) {
        return isPressed(keyBinding.getPrimaryKey()) || isPressed(keyBinding.getSecondaryKey());
    }

    public boolean isPressed(Key key) {
        return isPressed(key.getKeyCode());
    }

    private boolean isPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER) {
            return keyStates[keyCode] && !previousKeyStates[keyCode];
        } else {
            return keyStates[keyCode];
        }
    }

    private void setCurrentKeyStatesAsPreviousKeyStates() {
        int keyStatesPosition = 0;
        int previousKeyStatesPosition = 0;
        System.arraycopy(keyStates, keyStatesPosition, previousKeyStates, previousKeyStatesPosition, MAX_KEY_CODE);
    }

}