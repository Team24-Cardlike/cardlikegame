package org.example.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.example.Model.GameManager;
import org.example.Model.Save;

public class MenuController {
    GameManager manager;
    Save save;

    public MenuController(GameManager manager, Save save) {
        this.manager = manager;
        this.save = save;
    }

    public void setDifficulty(String s){
        manager.setDifficulty(s);
    }

    public void startGame() {
        manager.startGame();
    }
    public boolean loadGame() {
        return save.loadGame();
    }

}
