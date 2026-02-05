package com.logandhillon.logangamelib.engine.disk;

import com.logandhillon.logangamelib.engine.LGLGameHandler;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Contains helper methods for generating platform {@link Path} objects
 *
 * @author Logan Dhillon
 */
@SuppressWarnings("ClassCanBeRecord")
public class PathManager {
    private static final Logger LOG      = LoggerContext.getContext().getLogger(PathManager.class);
    private static final String PLATFORM = System.getProperty("os.name").toLowerCase();

    private final Path base;

    /**
     * Creates a new path manager given a specified game handler
     *
     * @param game game handler
     */
    public PathManager(LGLGameHandler game) {
        Path path;

        String devMode = System.getenv("LGL_DEV_MODE");
        if (devMode != null && devMode.equalsIgnoreCase("true")) {
            path = Paths.get("run");
            LOG.info("Starting in dev mode, base path will resolve to {}", path);
        } else if (PLATFORM.contains("win")) {
            // app data or user home if not found
            String appData = System.getenv("APPDATA");
            path = Paths.get(appData != null ? appData : System.getProperty("user.home"));
        } else if (PLATFORM.contains("mac")) {
            // logs folder in user home
            path = Paths.get(System.getProperty("user.home"), "Library", "Application Support");
        } else {
            // linux...
            String xdg = System.getenv("XDG_CONFIG_HOME");
            path = Paths.get(xdg != null ? xdg : Paths.get(System.getProperty("user.home"), ".config").toString());
        }

        LOG.info("Computed path as {}", path);
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
}
