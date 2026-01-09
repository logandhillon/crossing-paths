package com.logandhillon.fptgame.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * An abstract resource loader
 *
 * @param <T> the type that this loader returns programmatically (e.g. text loader is {@link String}
 *
 * @author Logan Dhillon
 */
public abstract class Resource<T> implements AutoCloseable {
    protected final InputStream stream;

    /**
     * Creates a new resource and opens an {@link InputStream} for it.
     *
     * @param path the relative path to the resource
     *
     * @throws FileNotFoundException if the file doesn't exist
     */
    public Resource(String path) throws FileNotFoundException {
        this.stream = TextResource.class.getResourceAsStream("/" + path);
        if (stream == null) throw new FileNotFoundException("Resource '" + path + "' not found");
    }

    public abstract T read() throws IOException;

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
