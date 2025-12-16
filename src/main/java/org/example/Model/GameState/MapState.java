package org.example.Model.GameState;

import org.example.Model.GameManager;
import org.example.Model.GameMap;

public class MapState implements GameState{
    GameMap map;
    String name = "map";
    @Override
    public void update(GameManager maneger) {
        map = maneger.gameMap;


    }

    @Override
    public String getName() {
        return name;
    }
}
