package no.taardal.pixelcave;

import no.taardal.pixelcave.gameframe.GameFrame;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class ITestKit {

    @MockBean
    private GameFrame gameFrame;

}
