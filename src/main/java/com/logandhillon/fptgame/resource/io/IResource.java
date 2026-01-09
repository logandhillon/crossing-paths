package com.logandhillon.fptgame.resource.io;

import java.io.IOException;

/**
 * @author Logan Dhillon
 */
public interface IResource<T> {
    T load() throws IOException;
}
