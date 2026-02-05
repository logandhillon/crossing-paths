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
        if (getInstance() != null) throw new IllegalStateException("(singleton) game handler already instantiated!");

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
}
