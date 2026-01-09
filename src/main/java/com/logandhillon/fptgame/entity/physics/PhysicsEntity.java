package com.logandhillon.fptgame.entity.physics;

/**
 * A physics entity is an {@link com.logandhillon.fptgame.entity.core.Entity} that (a) is affected by gravity, (b) has
 * collisions, and (c) has internal velocity that can be modulated to more (physically) accurately move the entity.
 *
 * @author Logan Dhillon
 */
public abstract class PhysicsEntity extends CollisionEntity {
    private static final float GRAVITY = 0.391f;
    private static final float MAX_VEL = 10f; // max scalar velocity
    private static final float GROUND_EPSILON = 0.01f;

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

    // Track if entity is on the ground for jumping logic
    private boolean grounded;

    @Override
    public void onUpdate(float dt) {
        // Apply gravity only if not grounded
        if (!grounded) vy += GRAVITY;

        // Clamp velocities
        if (vx > MAX_VEL) vx = MAX_VEL;
        if (vx < -MAX_VEL) vx = -MAX_VEL;
        if (vy > MAX_VEL) vy = MAX_VEL;
        if (vy < -MAX_VEL) vy = -MAX_VEL;

        // --- Horizontal movement ---
        translate(vx, 0);
        var e = getCollision();
        if (e != null) {
            if (vx > 0) translate(e.getX() - (getX() + getWidth()), 0);
            else if (vx < 0) translate(e.getX() + e.getWidth() - getX(), 0);
            vx = 0;
        }

        // --- Vertical movement ---
        translate(0, vy);
        e = getCollision();
        if (e != null) {
            if (vy > 0) {
                // Falling: snap to ground
                translate(0, e.getY() - (getY() + getHeight()));
                vy = 0;
            } else if (vy < 0) {
                // Rising: snap to ceiling
                translate(0, e.getY() + e.getHeight() - getY());
                vy = 0;
            }
        }

        // --- Ground probe (authoritative grounded check) ---
        translate(0, GROUND_EPSILON);
        e = getCollision();
        if (e != null) {
            grounded = true;
            translate(0, e.getY() - (getY() + getHeight()));
        } else {
            grounded = false;
            translate(0, -GROUND_EPSILON);
        }
    }

    public boolean isGrounded() {
        return grounded;
    }
}
