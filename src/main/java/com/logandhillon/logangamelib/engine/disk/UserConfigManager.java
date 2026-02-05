package com.logandhillon.logangamelib.engine.disk;

import com.logandhillon.fptgame.networking.proto.ConfigProto;
import com.logandhillon.fptgame.networking.proto.ConfigProto.UserConfig;
import com.logandhillon.logangamelib.engine.LGLGameHandler;
import javafx.scene.input.KeyCode;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Handles (de)serialization of save game data and (un)loading said data to the disk.
 *
 * @author Logan Dhillon
 */
public class UserConfigManager {
    private static final Logger     LOG            = LoggerContext.getContext().getLogger(UserConfigManager.class);
    private static final UserConfig DEFAULT_CONFIG = ConfigProto.UserConfig
            .newBuilder()
            .setName("Player")
            .setKeyMoveLeft(KeyCode.A.name())
            .setKeyMoveRight(KeyCode.D.name())
            .setKeyMoveJump(KeyCode.SPACE.name())
            .setKeyMoveInteract(KeyCode.E.name())
            .setMasterVolume(1f)
            .setMusicVolume(1f)
            .setSfxVolume(1f)
            .build();

    private final File file;

    /**
     * Creates a new user config manager
     *
     * @param game game handler
     */
    public UserConfigManager(LGLGameHandler game) {
        this.file = game.getPathManager().getFile("logangamelib.dat");
    }

    /**
     *
     * Creates a new user config manager with a custom filepath for the save file: the file that is managed by this
     * instance.
     *
     * @param game     game handler
     * @param filename name of file manage from this point on.
     */
    public UserConfigManager(LGLGameHandler game, String filename) {
        file = game.getPathManager().getFile(filename);
        LOG.info("Set managed file to {}", file.getAbsolutePath());
    }

    /**
     * Saves the provided {@link UserConfig} to the disk.
     *
     * @return user config that is now saved to disk
     *
     * @throws RuntimeException if the user config cannot be saved to disk
     */
    public UserConfig save(UserConfig config) {
        File parent = this.file.getParentFile();
        if (parent != null && !parent.exists()) {
            LOG.warn("Parent directory for user config file doesn't exist, creating folder(s).");
            //noinspection ResultOfMethodCallIgnored
            parent.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(this.file)) {
            LOG.info("Writing user configuration to {}", this.file.getAbsolutePath());
            config.writeTo(fos);
            return config;
        } catch (IOException e) {
            LOG.error("Failed to save user configuration to {}", file.getAbsolutePath(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Tries to load the {@link UserConfig} from disk, returning the {@link UserConfigManager#DEFAULT_CONFIG} if it (a)
     * fails or (b) doesn't exist.
     *
     * @return user config from disk
     */
    public UserConfig load() {
        // if there is no save file, save the default and return
        if (!file.exists()) {
            LOG.warn("Saved user config doesn't exist, saving default config to disk");
            save(DEFAULT_CONFIG);
            return DEFAULT_CONFIG;
        }

        UserConfig c;
        try (FileInputStream file = new FileInputStream(this.file)) {
            c = DEFAULT_CONFIG.toBuilder().mergeFrom(UserConfig.parseFrom(file)).build();
            LOG.info("Successfully loaded user config for '{}' from disk", c.getName());
        } catch (IOException e) {
            LOG.error("Failed to load user configuration from {}", file.getAbsolutePath(), e);
            return DEFAULT_CONFIG;
        }

        if (c.getName().isBlank()) {
            LOG.warn("Loaded name is blank, setting name to default (Player)");
            c.toBuilder().setName("Player");
            save(c);
        }

        return c;
    }

    /**
     * Updates only the fields specified and saves the resulting config.
     *
     * @param partial the partial values, whatever is set here will be updated, otherwise it will remain the same.
     */
    public UserConfig update(UserConfig current, UserConfig partial) {
        LOG.debug("Updating user config with {}", partial.toString());
        UserConfig merged = current.toBuilder().mergeFrom(partial).build();
        return save(merged);
    }
}
