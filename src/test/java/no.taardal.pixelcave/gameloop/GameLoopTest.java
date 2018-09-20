package no.taardal.pixelcave.gameloop;

import no.taardal.pixelcave.statemachine.GameStateManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class GameLoopTest {

    @MockBean
    private GameStateManager gameStateManagerMock;

    private GameLoop gameLoop;

    @Before
    public void setUp() {
        gameLoop = new GameLoop(gameStateManagerMock);
        doNothing().when(gameStateManagerMock).onUserInput();
        doNothing().when(gameStateManagerMock).onUpdate(anyFloat());
        doNothing().when(gameStateManagerMock).onDraw();
    }

    @After
    public void tearDown() {
        gameLoop.stop();
        assertFalse(gameLoop.isRunning());
    }

    @Test
    public void noUpdatesBeforeEnoughTimePassed() {
        gameLoop.setDelta(0);
        gameLoop.loop();

        verify(gameStateManagerMock, never()).onUserInput();
        verify(gameStateManagerMock, never()).onUpdate(anyFloat());
        verify(gameStateManagerMock, times(1)).onDraw();

        gameLoop.setDelta(GameLoop.UPDATE_DELTA_THRESHOLD);
        gameLoop.loop();

        verify(gameStateManagerMock, times(1)).onUserInput();
        verify(gameStateManagerMock, times(1)).onUpdate(anyFloat());
        verify(gameStateManagerMock, times(2)).onDraw();
    }

}
