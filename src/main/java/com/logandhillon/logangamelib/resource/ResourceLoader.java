package com.logandhillon.logangamelib.resource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Provides static methods for loading resources
 *
 * @author Logan Dhillon
 */
public class ResourceLoader {
    /**
     * Safely loads an {@link IResource} and returns the type of resource, catching the potential {@link IOException}
     * and throwing a runtime {@link ResourceLoaderException} instead.
     *
     * @param res resource loader
     * @param <T> type of resource
     *
     * @return the loaded resource
     */
    public static <T> T loadSafe(Class<? extends Resource<T>> resource, String pathname) {
        try (var res = resource.getDeclaredConstructor(String.class).newInstance(pathname)) {
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
