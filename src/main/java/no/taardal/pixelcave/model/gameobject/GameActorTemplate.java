package no.taardal.pixelcave.model.gameobject;

import no.taardal.pixelcave.model.animation.AnimationTemplate;
import no.taardal.pixelcave.model.animation.AnimationType;

import java.util.List;
import java.util.Map;

public class GameActorTemplate {

    private Map<AnimationType, AnimationTemplate> animationTemplates;
    private List<String> themes;
    private int approximateWidth;
    private int approximateHeight;

    public Map<AnimationType, AnimationTemplate> getAnimationTemplates() {
        return animationTemplates;
    }

    public void setAnimationTemplates(Map<AnimationType, AnimationTemplate> animationTemplates) {
        this.animationTemplates = animationTemplates;
    }

    public List<String> getThemes() {
        return themes;
    }

    public void setThemes(List<String> themes) {
        this.themes = themes;
    }

    public int getApproximateWidth() {
        return approximateWidth;
    }

    public void setApproximateWidth(int approximateWidth) {
        this.approximateWidth = approximateWidth;
    }

    public int getApproximateHeight() {
        return approximateHeight;
    }

    public void setApproximateHeight(int approximateHeight) {
        this.approximateHeight = approximateHeight;
    }

}
