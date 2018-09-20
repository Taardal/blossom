package no.taardal.pixelcave.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.taardal.pixelcave.jsondeserializer.*;
import no.taardal.pixelcave.layer.GameObjectLayer;
import no.taardal.pixelcave.layer.Layer;
import no.taardal.pixelcave.layer.TileLayer;
import no.taardal.pixelcave.service.ResourceService;
import no.taardal.pixelcave.tile.TileSet;
import no.taardal.pixelcave.world.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonProvider {

    @Bean
    @Autowired
    public Gson provideGson(ResourceService resourceService) {
        return new GsonBuilder()
                .registerTypeAdapter(World.class, new WorldDeserializer())
                .registerTypeAdapter(TileSet.class, new TileSetDeserializer(resourceService))
                .registerTypeAdapter(Layer.class, new LayerDeserializer())
                .registerTypeAdapter(TileLayer.class, new TileLayerDeserializer())
                .registerTypeAdapter(GameObjectLayer.class, new GameObjectLayerDeserializer())
                .create();
    }

}
