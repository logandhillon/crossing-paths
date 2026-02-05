package com.logandhillon.logangamelib.engine;

import com.logandhillon.logangamelib.engine.disk.PathManager;
import javafx.application.Application;

/**
 * @author Logan Dhillon
 */
public abstract class LGLGameHandler extends Application {
    private final LGLGameHandler instance;
    private final PathManager    pathMgr;
    private final String         gameId;

    /**
     * @param gameId all lowercase, snake_case game id
     *
     * @apiNote sets sys:LGL_BASE_PATH (system property) variable to the computed path
     */
    public LGLGameHandler(String gameId) {
        if (getInstance() != null) throw new IllegalStateException("(singleton) game handler already instantiated!");

        this.gameId = gameId;
        this.pathMgr = new PathManager(this);
        System.setProperty("LGL_BASE_PATH", this.pathMgr.base().toString());
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
