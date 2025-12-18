package org.example.Model;

import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeLibrary;


import java.util.ArrayList;
import java.util.Comparator;

public class Shop {
    ArrayList<String> stringList;
    UpgradeLibrary ul;
    GameManager manager;
    ArrayList<Upgrade> items;
    public Shop(GameManager manager){
        this.manager = manager;
        stringList = new ArrayList<>();
        ul = new UpgradeLibrary();
        items = new ArrayList<>();
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

    public ArrayList<Upgrade> getUpdatedList(User user){
            while(items.size() < 10){
                Upgrade random = ul.getRandomUpgrade();
                if(getUserUpgrades(user) != null){
                    if((items.isEmpty() || !items.contains(random)) && !getUserUpgrades(user).contains(random.getName())){
                        items.add(random);
                    }
                }
                else if(items.isEmpty() || !items.contains(random)){
                    items.add(random);
                }
            }
            return items;
    }

    public void removeFromItemList(Upgrade item){
        items.remove(item);
    }
}
