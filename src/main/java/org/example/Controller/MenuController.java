package org.example.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.example.Model.GameManager;
import org.example.Model.Save;

public class MenuController {
    GameManager manager;
    Save save;

    public MenuController(GameManager maneger, Save save) {
        this.manager = maneger;
        this.save = save;
    }

    public void startGame() {
        manager.startGame();
    }
    public void loadGame() {
        save.loadGame();
    }
}
