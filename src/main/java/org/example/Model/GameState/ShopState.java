package org.example.Model.GameState;

import org.example.Model.GameManager;

public class ShopState implements GameState{
    String name = "shop";

    @Override
    public String getName() {
        return this.name;
    }

    public void update(GameManager manager) {
       //shopView.update();
    }
}
