package com.logandhillon.fptgame.scene.menu;

import com.logandhillon.fptgame.entity.core.Entity;

public interface MenuContent {
    Entity[] getEntities();

    /**
     * Runs when this menu content is displayed by its parent {@link MenuHandler}
     */
    default void onShow() {}
}

