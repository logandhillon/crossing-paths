package com.logandhillon.logangamelib.engine;

import com.logandhillon.fptgame.GameHandler;
import com.logandhillon.logangamelib.engine.disk.PathManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.util.Optional;

/**
 * Abstract game handler for the logangamelib game engine.
 *
 * @author Logan Dhillon
 * @implNote this class shall be the entrypoint of your game, handling all primary logic.
 */
public abstract class LGLGameHandler extends Application {
    private static final Logger LOG = LoggerContext.getContext().getLogger(LGLGameHandler.class);

    private static LGLGameHandler instance;

    private final PathManager pathMgr;
    private final String      gameId;

    protected Stage        stage;
    protected GameScene<?> activeScene;

    protected boolean debugMode;

    /**
     * @param gameId all lowercase, snake_case game id
     */
    public LGLGameHandler(String gameId) {
        this.gameId = gameId;
        this.pathMgr = new PathManager(this);

        if (getInstance() != null) {
            LOG.warn("Created non-canonical LGL game handler instance");
        } else {
            LOG.info("Canonical game handler bound to {}", this);
            instance = this;
        }
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        // rename thread to shorten logs
        Thread.currentThread().setName("LGL-FX");

        stage.setOnCloseRequest(e -> {
            LOG.info("Received window close request");
            onShutdown();
            Platform.exit();
        });

        String flag = System.getenv("LGL_DEBUG_MODE");
        this.debugMode = flag != null && flag.equalsIgnoreCase("true");

        // register shutdown hook (handles SIGTERM/crashes)
        Runtime.getRuntime().addShutdownHook(new Thread(GameHandler.getInstance()::onShutdown, "LGL-ShutdownHook"));

        setScene(onStart(stage));

        stage.show();
    }

    /**
     * Runs when this game is launched.
     * <p>
     * Use this method to initialize the {@link Stage} and set up your game.
     */
    protected abstract GameScene<?> onStart(Stage stage);

    /**
     * Runs when this game is shutdown or otherwise closed.
     * <p>
     * Use this method to close/terminate any threads, handle last-minute clean up, etc.
     */
    protected abstract void onShutdown();

    /**
     * @return the name of the game in snake_case.
     */
    public String getGameId() {
        return gameId;
    }

    public static LGLGameHandler getInstance() {
        return instance;
    }

    public PathManager getPathManager() {
        return pathMgr;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Tries to return the active scene as the (expected) type, casting it to said type, and returning null if such
     * fails.
     *
     * @param type the expected type of {@link GameScene}
     *
     * @return the active {@link GameScene} if it is the right type, or null if it's not
     */
    public <T extends GameScene<?>> Optional<T> getActiveScene(Class<T> type) {
        if (!type.isInstance(activeScene))
            return Optional.empty();

        return Optional.of(type.cast(activeScene));
    }

    /**
     * Discards the currently active scene and replaces it with the provided one.
     *
     * @param scene the GameScene to switch
     */
    public void setScene(GameScene<?> scene) {
        activeScene = GameEngine.setScene(this, stage, activeScene, scene);
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
