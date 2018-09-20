package no.taardal.pixelcave.config;

import no.taardal.pixelcave.actor.ActorType;
import no.taardal.pixelcave.sprite.ActorTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties
public class GameConfig {

    private String title;
    private int width;
    private List<String> levels;
    private Map<ActorType, ActorTemplate> templates;

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

    public List<String> getLevels() {
        return levels;
    }

    public void setLevels(List<String> levels) {
        this.levels = levels;
    }

    public Map<ActorType, ActorTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<ActorType, ActorTemplate> templates) {
        this.templates = templates;
    }

}
