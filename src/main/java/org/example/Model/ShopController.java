package org.example.Model;

import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeLibrary;

import java.util.ArrayList;

public class ShopController {
    Round round;
    ArrayList<String> stringList;
    User user;
    UpgradeLibrary ul;
    GameManager manager;
    public ShopController(User user, GameManager manager){
        //this.round = round;
        this.user = user;
        this.manager = manager;
        stringList = new ArrayList<>();
        ul = new UpgradeLibrary();
    }

    public UpgradeLibrary getUpgradeLibrary(){
        return ul;
    }

    public void upgradeBaught(Upgrade upgrade){
        user.setUsersUpgrades(upgrade);
    }

    public void closeShop(){
        manager.closeShop();
    }

    public ArrayList<String> getUserUpgrades(){
        stringList.clear();
        for(int i = 0; i < user.usersUpgrades.size(); i++){
            stringList.add(user.usersUpgrades.get(i).getName());
        }
        return stringList;
    }

    public void switchState(){
        //manager.setState();
    }
}
