package com.logandhillon.logangamelib.resource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Provides static methods for loading resources
 *
 * @author Logan Dhillon
 */
public class ResourceLoader {
    private static final Logger LOG = LoggerContext.getContext().getLogger(ResourceLoader.class);

    /**
     * Safely loads an {@link IResource} and returns the type of resource, catching the potential {@link IOException}
     * and throwing a runtime {@link ResourceLoaderException} instead.
     *
     * @param resource resource loader class
     * @param pathname path to resource
     * @param <T>      type of resource
     *
     * @return the loaded resource
     */
    public static <T> T loadSafe(Class<? extends Resource<T>> resource, String pathname) {
        try (var res = resource.getDeclaredConstructor(String.class).newInstance(pathname)) {
            LOG.debug("Loaded {} from {}", resource.getSimpleName(), pathname);
            return res.load();
        } catch (IOException e) {
            throw new ResourceLoaderException(e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ResourceLoaderException extends RuntimeException {
        public ResourceLoaderException(Throwable cause) {
            super("Failed to load resource", cause);
        }
    }
}
