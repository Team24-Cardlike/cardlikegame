package org.example.Controller;

import org.example.Model.GameManager;
import org.example.Model.GameMap;

import java.util.ArrayList;

public class MapController {
    GameMap map;
    GameManager manager;
    public MapController(GameManager manager){
        this.manager = manager;
    }

    public void setMap(GameMap gameMap) {
        this.map = gameMap;
    }

    public void selectLvl(String opName) {
        map.levelSelect(opName);
    }
}
