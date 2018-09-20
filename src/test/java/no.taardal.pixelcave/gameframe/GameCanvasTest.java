package no.taardal.pixelcave.gameframe;

import no.taardal.pixelcave.camera.Camera;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GameCanvasTest {

    @MockBean
    private Camera cameraMock;

    @Test
    public void scalesToExpectedSize() {
        int width = 200;
        int height = width / 16 * 9;

        when(cameraMock.getWidth()).thenReturn(width);
        when(cameraMock.getHeight()).thenReturn(height);

        GameCanvas gameCanvas = new GameCanvas(cameraMock);

        Dimension dimension = gameCanvas.getPreferredSize();
        assertSame(width * GameCanvas.SCALE, dimension.getWidth());
        assertSame(height * GameCanvas.SCALE, dimension.getHeight());
    }
}
