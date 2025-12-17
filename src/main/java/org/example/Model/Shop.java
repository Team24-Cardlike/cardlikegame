package org.example.Model;

import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeLibrary;


import java.util.ArrayList;
import java.util.Comparator;

public class Shop {
    ArrayList<String> stringList;
    UpgradeLibrary ul;
    GameManager manager;
    public Shop(GameManager manager){
        this.manager = manager;
        stringList = new ArrayList<>();
        ul = new UpgradeLibrary();
    }

    public UpgradeLibrary getUpgradeLibrary(){
        return ul;
    }

    public boolean upgradeBought(Upgrade upgrade, User user){
        return user.buyUpgrade(upgrade);
    }

    public ArrayList<String> getUserUpgrades(User user){
        stringList.clear();
        for(int i = 0; i < user.upgrades.size(); i++){
            stringList.add(user.upgrades.get(i).getName());
        }
        return stringList;
    }

    public void switchState(){
        //manager.setState();
    }
}
