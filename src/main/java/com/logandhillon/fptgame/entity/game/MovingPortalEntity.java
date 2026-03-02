package com.logandhillon.fptgame.entity.game;

import com.logandhillon.fptgame.networking.proto.LevelProto;
import com.logandhillon.logangamelib.gfx.AtlasTile;

import static com.logandhillon.logangamelib.entity.physics.PhysicsEntity.PX_PER_METER;

public class MovingPortalEntity extends PortalEntity{

    private final float destX;
    private final float destY;
    private final float originX;
    private final float originY;
    private final float speed;

    /**
     * true=going towards destination; false=going towards original pos
     */
    private boolean goingTowardsDest;
    private float   tx;
    private float   ty;
    /**
     * Creates a collidable entity at the specified position with the specified hitbox
     *
     * @param x     x-position (from left)
     * @param y     y-position (from top)
     * @param isRed true=red, false=blue
     */
    public MovingPortalEntity(float x, float y, float destX, float destY, float speed, boolean goingTowardsDest, boolean isRed) {
        super(x, y, isRed);
        this.destX = destX;
        this.destY = destY;
        this.originX = x;
        this.originY = y;
        this.speed = speed * PX_PER_METER;
        setGoingTowardsDest(goingTowardsDest);

    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);

        float dx = tx - x;
        float dy = ty - y;
        float delta = speed * dt; // meters to travel
        float dist = (float)Math.sqrt(dx * dx + dy * dy);

        // if already at destination, return
        if (dist == 0f) return;

        if (dist <= delta) setPosition(tx, ty);
        else translate(dx / dist * delta, dy / dist * delta);
    }

    public void setGoingTowardsDest(boolean goingTowardsDest) {
        this.goingTowardsDest = goingTowardsDest;
        tx = goingTowardsDest ? destX : originX;
        ty = goingTowardsDest ? destY : originY;
    }

    public void invertGoingTowardsDest() {
        setGoingTowardsDest(!goingTowardsDest);
    }

    @Override
    public LevelProto.LevelObject serialize() {
        return LevelProto.LevelObject
                .newBuilder()
                .setX(x).setY(y)
                .setMovingPortal(LevelProto.MovingPortal
                                           .newBuilder()
                                           .setDestX(destX)
                                           .setDestY(destY)
                                           .setSpeed(speed)
                                           .build())
                .build();
    }

    public static MovingPortalEntity load(LevelProto.LevelObject msg) {
        return new MovingPortalEntity(
                msg.getX(),
                msg.getY(),
                msg.getMovingPortal().getDestX(),
                msg.getMovingPortal().getDestY(),
                msg.getMovingPortal().getSpeed(),
                msg.getMovingPortal().getGoingTowardsDest(),
                msg.getMovingPortal().getIsRed());
    }
}
