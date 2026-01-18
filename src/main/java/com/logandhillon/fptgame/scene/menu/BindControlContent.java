package com.logandhillon.fptgame.scene.menu;

import com.logandhillon.fptgame.GameHandler;
import com.logandhillon.fptgame.resource.Colors;
import com.logandhillon.fptgame.resource.Fonts;
import com.logandhillon.logangamelib.entity.Entity;
import com.logandhillon.logangamelib.entity.ui.ModalEntity;
import com.logandhillon.logangamelib.entity.ui.TextEntity;
import javafx.geometry.VPos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Bind control menus are encountered briefly when users change their control bindings in the {@link SettingsMenuContent}
 * menu
 *
 * @author Jack Ross
 */
public class BindControlContent implements MenuContent{
    private final        Entity[] entities;
    private static final Font    INSTRUCTION_FONT = Font.font(Fonts.TREMOLO, FontWeight.MEDIUM, 20);
    private final MenuHandler menu;
    private              KeyCode result;

    /**
     * Creates binding menu
     *
     * @param menu the {@link MenuHandler} responsible for switching active scenes.
     */
    public BindControlContent(MenuHandler menu){
        this.menu = menu;
        entities = new Entity[]{
                new ModalEntity(0, 0, GameHandler.CANVAS_WIDTH, GameHandler.CANVAS_HEIGHT),

                // instruction text
                new TextEntity.Builder(GameHandler.CANVAS_WIDTH / 2f, GameHandler.CANVAS_HEIGHT/ 2f)
                        .setText("PRESS ANY BUTTON")
                        .setFont(INSTRUCTION_FONT)
                        .setColor(Colors.ACTIVE)
                        .setAlign(TextAlignment.CENTER)
                        .setBaseline(VPos.CENTER)
                        .build()};
        menu.addHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);
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

    @Override
    public void onDestroy() {
        //TODO: Set binding here when framework is in place
    }

    private void onKeyPressed(KeyEvent e){
        //FIX: This isn't working for some reason
        if (!(e.getCode().isLetterKey())) return;
        result = e.getCode();
        menu.removeContent(this);
    }

    private KeyCode getResult(){
        return result;
    }
}
