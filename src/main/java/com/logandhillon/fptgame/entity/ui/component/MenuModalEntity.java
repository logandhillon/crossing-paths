package com.logandhillon.fptgame.entity.ui.component;

import com.logandhillon.fptgame.entity.core.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The modal displayed on the left side in every menu screen
 *
 * @author Jack Ross
 */
public class MenuModalEntity extends ModalEntity{
    /**
     * Creates an entity at the specified position. All entities passed to this modal will be translated such that (0, 0) is
     * the top-left corner of this modal.
     *
     * @param x x-position (from left)
     * @param y y-position (from top)
     * @param w width of modal
     * @param h height of modal
     */
    public MenuModalEntity(float x, float y, float w, float h, Entity... entities) {
        super(x, y, w, h, entities);
    }

    /**
     * Renders the polygon modal used in menu screens
     *
     * @param g the graphical context to render to.
     * @param x the x position to render the entity at
     * @param y the y position to render the entity at
     *
     */
    @Override
    protected void onRender(GraphicsContext g, float x, float y) {
        double[] xPoints = new double[]{x, x + w, x + w - 75, x};
        double[] yPoints = new double[]{y, y, y + h, y + h};
        g.setFill(Color.rgb(0, 0, 0, 0.4));
        g.fillPolygon(xPoints, yPoints, 4);
    }
}
