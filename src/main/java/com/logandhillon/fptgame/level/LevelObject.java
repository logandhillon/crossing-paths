package com.logandhillon.fptgame.level;

import com.logandhillon.fptgame.networking.proto.LevelProto;
import com.logandhillon.logangamelib.entity.GameObject;
import com.logandhillon.logangamelib.networking.ProtoSerializable;

/**
 * A {@link GameObject} that may be serialized via {@link ProtoSerializable} to a {@link LevelProto.LevelObject}.
 *
 * @author Logan Dhillon
 * @implNote it is recommended to create a {@code load(LevelProto.LevelObject)} static method to convert Messages back
 * into a {@link LevelProto.LevelObject}.
 */
public interface LevelObject extends ProtoSerializable<LevelProto.LevelObject>, GameObject {
}
