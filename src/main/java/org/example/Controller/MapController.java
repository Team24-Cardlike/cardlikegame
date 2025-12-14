package org.example.Controller;

import org.example.Model.GameMap;
import org.example.Model.Save;

public class MapController {
    GameMap map;
    private Save save;

    public void setMap(GameMap gameMap, Save save) {
        this.map = gameMap;
        this.save = save;
    }

    public void selectLvl(String opName) {
        System.out.println(opName);
        map.levelSelect(opName);

    }
    public void save() {
        save.saveGame();
    }
}
