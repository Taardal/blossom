package no.taardal.pixelcave.sprite;

import no.taardal.pixelcave.animation.SpriteSheetCoordinate;
import no.taardal.pixelcave.animation.AnimationType;

import java.util.List;
import java.util.Map;

public class ActorTemplate {

    private Map<AnimationType, SpriteSheetCoordinate> spriteSheetCoordinates;
    private List<String> themes;
    private int approximateWidth;
    private int approximateHeight;

    public Map<AnimationType, SpriteSheetCoordinate> getSpriteSheetCoordinates() {
        return spriteSheetCoordinates;
    }

    public void setSpriteSheetCoordinates(Map<AnimationType, SpriteSheetCoordinate> spriteSheetCoordinates) {
        this.spriteSheetCoordinates = spriteSheetCoordinates;
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
