package org.example.Model.GameState;

import org.example.Model.GameManager;
import org.example.Model.ShopController;
import org.example.Views.ShopView;

public class ShopState implements GameState{
    String name = "shop";

    @Override
    public String getName() {
        return name;
    }

    public void update(GameManager manager) {
       //shopView.update();
    }
}
