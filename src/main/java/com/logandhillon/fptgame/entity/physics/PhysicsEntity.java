package com.logandhillon.fptgame.entity.physics;

/**
 * A physics entity is an {@link com.logandhillon.fptgame.entity.core.Entity} that (a) is affected by gravity, (b) has
 * collisions, and (c) has internal velocity that can be modulated to more (physically) accurately move the entity.
 *
 * @author Logan Dhillon
 */
public abstract class PhysicsEntity extends CollisionEntity {
    private static final float GRAVITY = 0.981f;

    public float vx, vy;

    /**
     * Creates a collidable entity at the specified position with the specified hitbox
     *
     * @param x x-position (from left)
     * @param y y-position (from top)
     * @param w width of hitbox
     * @param h height of hitbox
     */
    public PhysicsEntity(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void onUpdate(float dt) {
        vy += GRAVITY;
        x += vx;
        y += vy;
    }
}
