package no.taardal.pixelcave.statemanager;

import no.taardal.pixelcave.state.game.GameState;
import no.taardal.pixelcave.statemachine.GameStateManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GameStateManagerTest {

    @MockBean
    private GameState gameStateMock;

    private GameStateManager gameStateManager;

    @Before
    public void setUp() {

    }

    @Test
    public void changesGameStateOnUserInput() {

    }
}
