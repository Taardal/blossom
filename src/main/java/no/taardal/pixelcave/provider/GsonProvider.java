package no.taardal.pixelcave.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.taardal.pixelcave.jsondeserializer.*;
import no.taardal.pixelcave.domain.TileSet;
import no.taardal.pixelcave.domain.World;
import no.taardal.pixelcave.domain.layer.GameObjectLayer;
import no.taardal.pixelcave.domain.layer.Layer;
import no.taardal.pixelcave.domain.layer.TileLayer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonProvider {

    @Bean
    public Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(World.class, new WorldDeserializer())
                .registerTypeAdapter(TileSet.class, new TileSetDeserializer())
                .registerTypeAdapter(Layer.class, new LayerDeserializer())
                .registerTypeAdapter(TileLayer.class, new TileLayerDeserializer())
                .registerTypeAdapter(GameObjectLayer.class, new GameObjectLayerDeserializer())
                .create();
    }

}
