package com.logandhillon.fptgame.scene;

import com.logandhillon.fptgame.GameHandler;
import com.logandhillon.fptgame.entity.game.PlatformEntity;
import com.logandhillon.fptgame.entity.player.ControllablePlayerEntity;
import com.logandhillon.fptgame.entity.player.PlayerEntity;
import com.logandhillon.fptgame.entity.player.PlayerInputSender;
import com.logandhillon.fptgame.resource.Textures;
import com.logandhillon.logangamelib.engine.GameScene;
import com.logandhillon.logangamelib.entity.ui.TextEntity;

/**
 * Basic game scene with a player that can run around and interact with physics objects; to aid development.
 *
 * @author Logan Dhillon
 */
public class DebugGameScene extends GameScene {
    private final PlayerEntity other;

    public DebugGameScene() {
        GameHandler.NetworkRole role = GameHandler.getNetworkRole();

        addEntity(Textures.ocean8());

        addEntity(new PlatformEntity(0, 680, 1280, 40));
        addEntity(new PlatformEntity(200, 550, 200, 40));
        addEntity(new PlatformEntity(400, 400, 200, 40));
        addEntity(new PlatformEntity(600, 280, 200, 40));
        addEntity(new PlatformEntity(700, 100, 40, 300));
        addEntity(new PlatformEntity(1100, 200, 40, 300));

        var player = new ControllablePlayerEntity(1280 / 2f, 200,
                                                  role == GameHandler.NetworkRole.SERVER ? 0 : 1,
                                                  new PlayerInputSender());
        addEntity(player);

        other = new PlayerEntity(100, 500, role == GameHandler.NetworkRole.SERVER ? 1 : 0, null);
        addEntity(other);

        addEntity(new TextEntity.Builder(10, 30)
                          .setText(() -> String.format(
                                  """
                                  [PLAYER; YOU]
                                  isGrounded: %s
                                  pos: %.1f, %.1f
                                  vel: %.1f, %.1f
                                  collision: %s
                                  dir: %s

                                  [PLAYER; OTHER]
                                  isGrounded: %s
                                  pos: %.1f, %.1f
                                  vel: %.1f, %.1f
                                  collision: %s
                                  dir: %s""",

                                  player.isGrounded(),
                                  player.getX(), player.getY(),
                                  player.vx, player.vy,
                                  player.getCollision() != null,
                                  player.getMoveDirection(),

                                  other.isGrounded(),
                                  other.getX(), other.getY(),
                                  other.vx, other.vy,
                                  other.getCollision() != null,
                                  other.getMoveDirection()))
                          .setFontSize(14)
                          .build());
    }
}
