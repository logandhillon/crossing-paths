package com.logandhillon.fptgame.entity.ui.component;

import com.logandhillon.fptgame.resource.Colors;
import com.logandhillon.logangamelib.entity.ui.Draggable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.ArcType;

public class SliderEntity extends Draggable {
    private static final int BACKBONE_DIAMETER = 6;
    private static final int CIRCLE_DIAMETER   = 20;

    private float cx;

    private boolean active;

    /**
     * Creates slider at the specified position.
     *
     * @param x x-position (from left)
     * @param y y-position (from top)
     */
    public SliderEntity(float x, float y, float w, float h, float cx) {
        super(x, y, w, h);
        this.cx = cx;
    }

    @Override
    protected void onRender(GraphicsContext g, float x, float y) {
        // backbone
        g.setFill(Colors.ACTIVE_TRANS_50);
        g.fillRoundRect(x, y, w, h, BACKBONE_DIAMETER, BACKBONE_DIAMETER);

        // backbone filled part
        g.setFill(Colors.BUTTON_HOVER);
        g.fillRoundRect(x, y, cx - x + 5, h, BACKBONE_DIAMETER, BACKBONE_DIAMETER);

        // slider knob
        g.setFill(active ? Colors.SLIDER_HEAD_ACTIVE : Colors.SLIDER_HEAD);
        g.fillArc(cx, y - BACKBONE_DIAMETER, CIRCLE_DIAMETER, CIRCLE_DIAMETER, 0, 360, ArcType.ROUND);
    }

    @Override
    public void onUpdate(float dt) {}

    @Override
    public void onDestroy() {}

    @Override
    public void onClick(MouseEvent e) {}

    @Override
    public void onMouseDown(MouseEvent e) {
        active = true;
        cx = (float)e.getX();
    }

    @Override
    public void onMouseUp(MouseEvent e) {
        if (active) active = false;
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if (!this.active) return;
        cx = (float)e.getX();
    }

    public float getValue() {
        return (cx - x + (CIRCLE_DIAMETER / 2f)) / w;
    }
}
