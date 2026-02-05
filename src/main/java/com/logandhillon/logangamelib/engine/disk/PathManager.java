package com.logandhillon.logangamelib.engine.disk;

import com.logandhillon.logangamelib.engine.LGLGameHandler;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Contains helper methods for generating platform {@link Path} objects
 *
 * @author Logan Dhillon
 */
@SuppressWarnings("ClassCanBeRecord")
public class PathManager {
    private static final Logger LOG      = LoggerContext.getContext().getLogger(PathManager.class);

    private final Path base;

    /**
     * Creates a new path manager given a specified game handler
     *
     * @param game game handler
     *
     * @apiNote sys:LGL_BASE_PATH (system property) must be set!
     */
    public PathManager(LGLGameHandler game) {
        Path path = tryCreatePath(System.getProperty("LGL_BASE_PATH")).orElseThrow(
                () -> new IllegalStateException("LGL_BASE_PATH is not set!"));

        LOG.info("Read LGL base path as {}", path);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            LOG.fatal("Failed to create logangamelib base directories");
            throw new RuntimeException(e);
        }
        base = path.resolve(Path.of("logangamelib", game.getGameId()));
    }

    /**
     * @return base path of this logangamelib game
     */
    public Path base() {
        return base;
    }

    /**
     * resolves a path in the base path of this game
     *
     * @param other path to resolve
     *
     * @return real I/O path on disk
     */
    public Path resolve(Path other) {
        return base.resolve(other);
    }

    /**
     * resolves a path in the base path of this game
     *
     * @param other path to resolve
     *
     * @return real I/O path on disk
     */
    public Path resolve(String other) {
        return base.resolve(other);
    }

    /**
     * Checks whether a string represents a syntactically valid path and ensures the path exists.
     * <p>
     * If the path does not already exist, this method will attempt to create it (including any missing parent
     * directories).
     * </p>
     *
     * @param path The path string to validate and create if necessary.
     *
     * @return an {@link Optional} {@link Path} if the path is valid and exists (or was successfully created);
     * {@link Optional#empty()} if the path is invalid or cannot be created due to permission or access errors.
     */
    public static Optional<Path> tryCreatePath(String path) {
        if (path == null || path.isEmpty()) return Optional.empty();

        try {
            Path p = Paths.get(path);
            Files.createDirectories(p);
            return Optional.of(p);
        } catch (InvalidPathException | IOException e) {
            return Optional.empty();
        }
    }
}
