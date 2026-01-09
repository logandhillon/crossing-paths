package com.logandhillon.fptgame.scene.menu;

import com.logandhillon.fptgame.GameHandler;
import com.logandhillon.fptgame.engine.MenuController;
import com.logandhillon.fptgame.entity.core.Entity;
import com.logandhillon.fptgame.entity.ui.component.InputBoxEntity;
import com.logandhillon.fptgame.entity.ui.component.MenuButton;
import com.logandhillon.fptgame.entity.ui.component.MenuModalEntity;
import com.logandhillon.fptgame.entity.ui.component.ModalEntity;
import com.logandhillon.fptgame.networking.proto.ConfigProto;

import static com.logandhillon.fptgame.GameHandler.CANVAS_HEIGHT;
import static com.logandhillon.fptgame.GameHandler.CANVAS_WIDTH;

/**
 * The main menu allows the user to navigate to other submenus, play or quit the game, and view game branding.
 *
 * @author Logan Dhillon, Jack Ross
 */
public class MainMenuContent implements MenuContent {
    private final Entity[] entities;

    private final InputBoxEntity userInput;

    /**
     * Creates a new main menu
     *
     * @param menu the main class that can switch scenes, manage connections, etc.
     */
    public MainMenuContent(MenuHandler menu) {
        float x = 30f;
        int dy = 48 + 16; // ∆y per button height
        int y = 448;

        userInput = new InputBoxEntity(16, 47, 316, "YOUR NAME", "YOUR NAME", 20);
        userInput.setInput(GameHandler.getUserConfig().getName());
        userInput.setOnBlur(() -> GameHandler.updateUserConfig(
                ConfigProto.UserConfig.newBuilder().setName(userInput.getInput()).buildPartial()));

        MenuController controller = new MenuController(
                () -> !userInput.getIsActive(),
                new MenuButton("Host Game", x, y, 256, 48, () -> menu.setContent(new HostGameContent(menu))),
                new MenuButton("Join Game", x, y + dy, 256, 48, () -> menu.setContent(
                        new JoinGameContent(menu, addr -> System.out.println("NOT IMPLEMENTED!")))),
                new MenuButton("Level Creator", x, y + 2 * dy, 256, 48, () -> {
                }),
                new MenuButton("", x, y + 3 * dy, 120, 48, () -> System.exit(0)),
                new MenuButton("", x + 136, y + 3 * dy, 120, 48, () -> System.exit(0))
        );

        // creates list of entities to be used by menu handler
        entities = new Entity[]{ new MenuModalEntity(0, 0, 442, CANVAS_HEIGHT), new ModalEntity(896, 79, 434, 131, new InputBoxEntity(20, 57, 336, "Player1", "YOUR NAME", 20)), controller};
    }

    /**
     * Allows {@link MenuHandler} to access content for this menu
     *
     * @return entity list
     */
    @Override
    public Entity[] getEntities() {
        return entities;
    }
}