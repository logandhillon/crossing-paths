package com.logandhillon.fptgame.scene.menu;

import com.logandhillon.fptgame.GameHandler;
import com.logandhillon.fptgame.entity.ui.component.MenuButton;
import com.logandhillon.fptgame.entity.ui.component.MenuModalEntity;
import com.logandhillon.fptgame.entity.ui.component.SliderEntity;
import com.logandhillon.fptgame.resource.Colors;
import com.logandhillon.fptgame.resource.Fonts;
import com.logandhillon.logangamelib.entity.Entity;
import com.logandhillon.logangamelib.entity.Renderable;
import com.logandhillon.logangamelib.entity.ui.TextEntity;
import javafx.geometry.VPos;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The settings menu allows users to customize audio and gameplay settings
 *
 * @author Jack Ross
 * @see BindControlContent
 */
public class SettingsMenuContent implements MenuContent{
    private final        String   HEADER      = "SETTINGS";
    private static final Font     HEADER_FONT = Font.font(Fonts.TREMOLO, FontWeight.MEDIUM, 32);
    private static final Font     SUBHEADER_FONT = Font.font(Fonts.TREMOLO, FontWeight.MEDIUM, 24);
    private static final Font     CONTROLS_FONT = Font.font(Fonts.TREMOLO, FontWeight.MEDIUM, 19);

    private final        Entity[] entities;
    private              KeyCode  moveLeft;
    private              KeyCode  moveRight;
    private              KeyCode  jump;
    private              KeyCode  interact;

    /**
     * Creates content for settings menu
     *
     * @param menu the {@link MenuHandler} responsible for switching active scenes.
     */
    public SettingsMenuContent(MenuHandler menu){

        // volume sliders
        SliderEntity master = new SliderEntity(32, 227, 327, 6, 190);

        SliderEntity music = new SliderEntity(32, 296, 327, 6, 190);

        SliderEntity sfx = new SliderEntity(32, 365, 327, 6, 190);

        entities = new Entity[]{
                new MenuModalEntity(
                        0, 0, 442, GameHandler.CANVAS_HEIGHT, true, menu),

                // labels/menu headers
                new TextEntity.Builder(32, 66)
                        .setColor(Colors.ACTIVE)
                        .setText(HEADER.toUpperCase())
                        .setFont(HEADER_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                new TextEntity.Builder(32, 140)
                        .setColor(Colors.ACTIVE)
                        .setText("AUDIO")
                        .setFont(SUBHEADER_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                new TextEntity.Builder(32, 410)
                        .setColor(Colors.ACTIVE)
                        .setText("CONTROLS")
                        .setFont(SUBHEADER_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                new TextEntity.Builder(32, 187)
                        .setColor(Colors.ACTIVE)
                        .setText("MASTER VOLUME")
                        .setFont(CONTROLS_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                new TextEntity.Builder(32, 256)
                        .setColor(Colors.ACTIVE)
                        .setText("MUSIC VOLUME")
                        .setFont(CONTROLS_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                new TextEntity.Builder(32, 325)
                        .setColor(Colors.ACTIVE)
                        .setText("SFX VOLUME")
                        .setFont(CONTROLS_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                new TextEntity.Builder(32, 464.5f)
                        .setColor(Colors.ACTIVE)
                        .setText("MOVE LEFT")
                        .setFont(CONTROLS_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                new TextEntity.Builder(32, 520.5f)
                        .setColor(Colors.ACTIVE)
                        .setText("MOVE RIGHT")
                        .setFont(CONTROLS_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                new TextEntity.Builder(32, 576.5f)
                        .setColor(Colors.ACTIVE)
                        .setText("JUMP")
                        .setFont(CONTROLS_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                new TextEntity.Builder(32, 632.5f)
                        .setColor(Colors.ACTIVE)
                        .setText("INTERACT")
                        .setFont(CONTROLS_FONT)
                        .setBaseline(VPos.TOP)
                        .build(),

                // underlines
                new Renderable(32, 168, (g, x, y) -> {
                    g.setStroke(Colors.ACTIVE);
                    g.setLineWidth(2);
                    g.strokeLine(x, y, x + 70, y);
                }),

                new Renderable(32, 439, (g, x, y) -> {
                    g.setStroke(Colors.ACTIVE);
                    g.setLineWidth(2);
                    g.strokeLine(x, y, x + 113, y);
                }),

                // control buttons
                new MenuButton("A", 279, 457, 80, 40, () -> menu.addContent(new BindControlContent(menu))),

                new MenuButton("D", 279, 513, 80, 40, () -> menu.addContent(new BindControlContent(menu))),

                new MenuButton("SPACE", 279, 569, 80, 40, () -> menu.addContent(new BindControlContent(menu))),

                new MenuButton("E", 279, 625, 80, 40, () -> menu.addContent(new BindControlContent(menu))),

                master, music, sfx };
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

    public KeyCode getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(KeyCode key) {
        moveLeft = key;
    }

    public KeyCode getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(KeyCode key) {
        moveRight = key;
    }

    public KeyCode getJump() {
        return jump;
    }

    public void setJump(KeyCode key) {
        jump = key;
    }

    public KeyCode getInteract() {
        return interact;
    }

    public void setInteract(KeyCode key) {
        interact = key;
    }
}
