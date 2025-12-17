package org.example.Controller;

import org.example.Model.GameManager;
import org.example.Model.Round;
import org.example.Model.Shop;
import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeLibrary;
import org.example.Model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class ShopController {
    Shop shop;
    ArrayList<String> stringList;
    User user;
    GameManager manager;
    public ShopController(GameManager manager){
        //this.round = round;
        this.user = manager.getUser();
        this.manager = manager;
        this.stringList = new ArrayList<>();
        this.shop = manager.gameMap.getShop();
    }

    public UpgradeLibrary getUpgradeLibrary(){
        return this.shop.getUpgradeLibrary();
    }

    public boolean upgradeBought(Upgrade upgrade){
        return this.shop.upgradeBought(upgrade, this.user);
    }

    public void closeShop(){
       this.manager.closeShop();
    }

    public ArrayList<String> getUserUpgrades(){
        return this.stringList = shop.getUserUpgrades(this.user);
    }
    public getUpdatedList(){
        //shop.getUpdatedList();
    }
}
