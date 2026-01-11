package com.logandhillon.fptgame.gfx;

import com.logandhillon.fptgame.resource.TextureAtlas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Logan Dhillon
 */
public class AnimationSequence {
    private final int[]        frames;
    private final int          fps;
    private final int          frameCount;
    private final TextureAtlas atlas;
    private final boolean      isInstance;

    private int   frame     = 0;
    private float frameTime = 0; // delta time since last frame

    private AnimationSequence(TextureAtlas atlas, int fps, boolean isInstance, int... frames) {
        if (frames.length % 2 != 0) throw new IllegalArgumentException("frame count must be even! (pairs of row, col)");

        this.atlas = atlas;
        this.frames = frames;
        this.fps = fps;
        this.frameCount = frames.length / 2;
        this.isInstance = isInstance;
    }

    public AnimationSequence(TextureAtlas atlas, int fps, int... frames) {
        this(atlas, fps, false, frames);
    }

    public void nextFrame() {
        if (!isInstance)
            throw new IllegalStateException("cannot set the frame of a static AnimationSequence. " +
                                            "use instance() on the sequence to get a new instance.");

        frame = Math.min(frame + 1, frameCount);
        frameTime = 0;
    }

    public void onUpdate(float dt) {
        frameTime += dt;
        if (frameTime >= fps) nextFrame();
    }

    public void draw(GraphicsContext g, float x, float y, float w, float h) {
        atlas.draw(g, frames[frame], frames[frame + 1], x, y, w, h);
    }

    public void draw(GraphicsContext g, float x, float y, float w, float h, Color color) {
        atlas.draw(g, frames[frame], frames[frame + 1], x, y, w, h, color);
    }

    public AnimationSequence instance() {
        return new AnimationSequence(atlas, fps, true, frames);
    }
}
