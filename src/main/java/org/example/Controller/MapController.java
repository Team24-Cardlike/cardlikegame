package org.example.Controller;

import org.example.Model.GameManager;
import org.example.Model.GameMap;
import org.example.Model.Save;

import java.util.ArrayList;

public class MapController {
    GameMap map;
    private Save save;
    GameManager manager;
    public MapController(GameManager manager){
        this.manager = manager;
    }

    public void setMap(GameMap gameMap, Save save) {
        this.map = gameMap;
        this.save = save;
    }

    public void selectLvl(String opName) {
        map.levelSelect(opName);
    }
    public void save() {
        save.saveGame(); 
    }

    public void openShop(){
        manager.setShopState();
    }


    public void updateManager() {
        manager.gameLoop();
    }
}
