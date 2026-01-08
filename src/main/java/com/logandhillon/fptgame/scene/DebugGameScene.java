package com.logandhillon.fptgame.scene;

import com.logandhillon.fptgame.engine.GameScene;
import com.logandhillon.fptgame.entity.game.PlatformEntity;
import com.logandhillon.fptgame.entity.game.PlayerEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static com.logandhillon.fptgame.GameHandler.CANVAS_HEIGHT;
import static com.logandhillon.fptgame.GameHandler.CANVAS_WIDTH;

/**
 * Basic game scene with a player that can run around and interact with physics objects; to aid development.
 *
 * @author Logan Dhillon
 */
public class DebugGameScene extends GameScene {
    public DebugGameScene() {
        addEntity(new PlatformEntity(0, 680, 1280, 720 - 680));
        addEntity(new PlayerEntity(1280 / 2f, 200));
    }

    @Override
    protected void render(GraphicsContext g) {
        // background
        g.setFill(Color.WHITESMOKE);
        g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        // render all other entities
        super.render(g);
    }
}
