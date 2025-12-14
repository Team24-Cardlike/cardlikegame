package org.example.Model.GameState;

import com.badlogic.gdx.Screen;
import com.sun.tools.javac.Main;
import org.example.Controller.MenuController;
import org.example.Model.GameManager;
import org.example.Model.ShopController;
import org.example.Views.MainMenuView;
import org.example.Views.ShopView;

public class MenuState implements GameState{
    String name = "menu";

    @Override
    public void update(GameManager maneger) {


    }

    @Override
    public String getName() {
        return name;
    }
}
