package org.example.Model.GameState;

import org.example.Model.GameManager;

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
