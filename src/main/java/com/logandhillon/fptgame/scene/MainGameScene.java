package com.logandhillon.fptgame.scene;

import com.logandhillon.fptgame.engine.GameScene;
import com.logandhillon.fptgame.resource.Colors;
import javafx.scene.canvas.GraphicsContext;

import static com.logandhillon.fptgame.GameHandler.CANVAS_HEIGHT;
import static com.logandhillon.fptgame.GameHandler.CANVAS_WIDTH;

public class MainGameScene extends GameScene {
    public MainGameScene() {
    }

    @Override
    protected void render(GraphicsContext g) {
        // background
        g.setFill(Colors.FOREGROUND);
        g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        // render all other entities
        super.render(g);
    }
}
