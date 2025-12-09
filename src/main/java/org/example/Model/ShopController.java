package org.example.Model;

import org.example.Model.Upgrades.Upgrade;

import java.util.ArrayList;

public class ShopController {
    Game game;
    ArrayList<String> stringList;
    public ShopController(Game game){
        this.game = game;
        stringList = new ArrayList<>();
    }

    public void upgradeBaught(Upgrade upgrade){
        game.user.setUsersUpgrades(upgrade);
    }
    public ArrayList<String> getUserUpgrades(){
        stringList.clear();
        for(int i = 0; i < game.user.usersUpgrades.size(); i++){
            stringList.add(game.user.usersUpgrades.get(i).getName());
        }
        return stringList;
    }
}
