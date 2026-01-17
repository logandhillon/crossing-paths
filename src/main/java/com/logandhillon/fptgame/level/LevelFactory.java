package com.logandhillon.fptgame.level;

import com.logandhillon.fptgame.entity.game.PlatformEntity;
import com.logandhillon.fptgame.networking.proto.LevelProto;

import java.util.List;

/**
 * @author Logan Dhillon
 */
public class LevelFactory {
    public static LevelObject loadObject(LevelProto.LevelObject msg) {
        return switch (msg.getDataCase()) {
            case PLATFORM -> PlatformEntity.load(msg);
            default -> throw new IllegalStateException("Illegal LevelObject type");
        };
    }

    public static List<LevelObject> load(LevelProto.LevelData data) {
        return data.getObjectsList().stream().map(LevelFactory::loadObject).toList();
    }
}
