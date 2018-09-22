package no.taardal.pixelcave.config;

import no.taardal.pixelcave.model.gameobject.GameActorType;
import no.taardal.pixelcave.model.gameobject.GameActorTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties
public class GameConfig {

    private String title;
    private int width;
    private int scale;
    private List<String> levels;
    private Map<GameActorType, GameActorTemplate> templates;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return width / 16 * 9;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public List<String> getLevels() {
        return levels;
    }

    public void setLevels(List<String> levels) {
        this.levels = levels;
    }

    public Map<GameActorType, GameActorTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<GameActorType, GameActorTemplate> templates) {
        this.templates = templates;
    }

}
