package no.taardal.pixelcave.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceService.class);

    public String readFile(String relativePath) {
        Path resourcePath = getResourcePath(relativePath);
        return resourcePath != null ? new String(getBytes(resourcePath)) : null;
    }

    public List<BufferedImage> getBufferedImages(String relativePath) {
        return listFiles(relativePath).stream()
                .sorted(Comparator.comparing(File::getName))
                .map(this::getBufferedImage)
                .collect(Collectors.toList());
    }

    public BufferedImage getBufferedImage(String relativePath) {
        Path resourcePath = getResourcePath(relativePath);
        return resourcePath != null ? getBufferedImage(resourcePath.toFile()) : null;
    }

    public List<File> listFiles(String relativePath) {
        return list(relativePath).stream().filter(file -> !file.isDirectory()).collect(Collectors.toList());
    }

    public List<File> list(String relativePath) {
        Path resourcePath = getResourcePath(relativePath);
        return resourcePath != null ? list(getResourcePath(relativePath)) : Collections.emptyList();
    }

    private Path getResourcePath(String relativePath) {
        URL url = getClass().getClassLoader().getResource(relativePath);
        if (url != null) {
            URI uri = getUri(url);
            if (uri != null) {
                return Paths.get(uri);
            }
        }
        return null;
    }

    private URI getUri(URL url) {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            LOGGER.error("Could not get URI from URL [{}]", url, e);
            return null;
        }
    }

    private List<File> list(Path path) {
        try {
            return Files.list(path).map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Could not list files and/or directories on path [{}]", path, e);
            return Collections.emptyList();
        }
    }

    private BufferedImage getBufferedImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            LOGGER.error("Could not read image on path [{}].", file.getPath(), e);
            return null;
        }
    }

    private byte[] getBytes(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            LOGGER.error("Could not read file from path [{}].", path, e);
            return new byte[]{};
        }
    }

}
