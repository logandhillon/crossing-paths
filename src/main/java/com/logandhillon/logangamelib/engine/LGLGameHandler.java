package com.logandhillon.logangamelib.engine;

import com.logandhillon.logangamelib.engine.disk.PathManager;
import javafx.application.Application;

/**
 * Abstract game handler for the logangamelib game engine.
 *
 * @author Logan Dhillon
 * @implNote this class shall be the entrypoint of your game, handling all primary logic.
 */
public abstract class LGLGameHandler extends Application {
    private final LGLGameHandler instance;
    private final PathManager    pathMgr;
    private final String         gameId;

    /**
     * @param gameId all lowercase, snake_case game id
     */
    public LGLGameHandler(String gameId) {
        if (getInstance() != null)
            throw new LGLInitializationException(
                    "LGL game handler already instantiated! There may only be ONE instance of the game handler.");

        this.gameId = gameId;
        this.pathMgr = new PathManager(this);
        instance = this;
    }

    /**
     * @return the name of the game in snake_case.
     */
    public String getGameId() {
        return gameId;
    }

    public LGLGameHandler getInstance() {
        return instance;
    }

    public PathManager getPathManager() {
        return pathMgr;
    }

    static {
        // throw errors if required sys properties aren't set, requires further checks as these are just null checks
        if (System.getProperty("LGL_BASE_PATH") == null)
            throw LGLInitializationException.missingProperty("LGL_BASE_PATH");
    }

    public static class LGLInitializationException extends RuntimeException {
        public LGLInitializationException(String msg) {
            super(msg);
        }

        public static LGLInitializationException missingProperty(String property) {
            return new LGLInitializationException("Required system property '" + property + "' is not set!");
        }
    }
}
