package org.example.Controller;

import org.example.Model.GameManager;
import org.example.Model.Round;
import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeLibrary;
import org.example.Model.User;

import java.util.ArrayList;
import java.util.Comparator;

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

    public void upgradeBought(Upgrade upgrade){
        user.addUpgrade(upgrade);
        user.getUpgrades().sort(Comparator.comparingInt(Upgrade::getIdNum));
    }

    public void closeShop(){
        manager.closeShop();
    }

    public ArrayList<String> getUserUpgrades(){
        stringList.clear();
        for(int i = 0; i < user.upgrades.size(); i++){
            stringList.add(user.upgrades.get(i).getName());
        }
        return stringList;
    }
}
