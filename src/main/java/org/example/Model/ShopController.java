package org.example.Model;

import org.example.Model.Upgrades.Upgrade;

public class ShopController {
    Game game;
    ShopController (Game game){
        this.game = game;
    }

    public void upgradeBaught(Upgrade upgrade){
        game.user.setUsersUpgrades(upgrade);
    }

}
