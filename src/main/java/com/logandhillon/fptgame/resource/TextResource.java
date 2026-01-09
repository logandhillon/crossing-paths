package com.logandhillon.fptgame.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A {@link AutoCloseable} resource manager that handles streaming IO from disk to the application for engine-layer
 * management.
 *
 * @author Logan Dhillon
 */
public class TextResource extends Resource<String> {
    public TextResource(String filename) throws FileNotFoundException {
        super(filename);
    }

    public String read() throws IOException {
        assert stream != null; // stream obviously isn't null bc resource will kill itself if it is
        return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    }
}
