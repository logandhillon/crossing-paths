package com.logandhillon.fptgame.entity.game;

import com.logandhillon.fptgame.entity.physics.PhysicsEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Logan Dhillon
 */
public class PlayerEntity extends PhysicsEntity {
    private static final int SIZE = 48;

    public PlayerEntity(float x, float y) {
        super(x, y, SIZE, SIZE);
    }

    @Override
    protected void onRender(GraphicsContext g, float x, float y) {
        g.setFill(Color.RED);
        g.fillRect(x, y, w, h);
    }

    @Override
    public void onDestroy() {

    }
}
