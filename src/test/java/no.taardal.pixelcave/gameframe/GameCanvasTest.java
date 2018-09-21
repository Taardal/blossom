package no.taardal.pixelcave.gameframe;

import no.taardal.pixelcave.config.GameConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class GameCanvasTest {

    @MockBean
    private GameConfig gameConfig;

    @Test
    public void scalesToExpectedSize() {
        int width = 200;
        int height = width / 16 * 9;
        int scale = 3;

        given(gameConfig.getWidth()).willReturn(width);
        given(gameConfig.getHeight()).willReturn(height);
        given(gameConfig.getScale()).willReturn(scale);

        GameCanvas gameCanvas = new GameCanvas(gameConfig);

        Dimension dimension = gameCanvas.getPreferredSize();
        assertEquals((double) (width * scale), dimension.getWidth(), 1);
        assertEquals((double) (height * scale), dimension.getHeight(), 1);

        then(gameConfig).should(times(1)).getWidth();
        then(gameConfig).should(times(1)).getHeight();
        then(gameConfig).should(times(2)).getScale();
    }
}
