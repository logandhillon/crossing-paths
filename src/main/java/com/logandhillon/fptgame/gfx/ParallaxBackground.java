package com.logandhillon.fptgame.gfx;

import com.logandhillon.fptgame.GameHandler;
import com.logandhillon.fptgame.entity.core.Entity;
import com.logandhillon.fptgame.resource.io.ImageResource;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.IOException;

/**
 * @author Logan Dhillon
 */
public class ParallaxBackground extends Entity {
    private final Layer[] layers;

    public ParallaxBackground(Layer... layers) {
        super(0, 0);
        this.layers = layers;
    }

    @Override
    protected void onRender(GraphicsContext g, float x, float y) {
        for (Layer layer: layers)
            g.drawImage(layer.image, layer.x, y, GameHandler.CANVAS_WIDTH, GameHandler.CANVAS_HEIGHT);
    }

    @Override
    public void onUpdate(float dt) {
        for (Layer layer: layers) layer.x += layer.speed * dt;
    }

    @Override
    public void onDestroy() {

    }

    public static final class Layer {
        private final Image image;
        private final float speed;

        private float x = 0;

        public Layer(String name, float speed) {
            try (var res = new ImageResource(name)) {
                this.image = res.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.speed = speed;
        }
    }
}
