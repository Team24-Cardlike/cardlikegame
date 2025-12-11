package org.example.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.example.Model.GameManager;

public class MenuController {
    GameManager maneger;

    public MenuController(GameManager maneger) {
        this.maneger = maneger;
    }

    public void startGame() {
        maneger.startGame();
    }
}
