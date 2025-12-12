package org.example.Controller;

import org.example.Model.GameMap;

public class MapController {
    GameMap map;

    public void setMap(GameMap gameMap) {
        this.map = gameMap;
    }

    public void selectLvl(String opName) {
        map.levelSelect(opName);
    }
}
