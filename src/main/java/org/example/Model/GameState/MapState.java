package org.example.Model.GameState;

import com.badlogic.gdx.Screen;
import org.example.Model.GameManager;
import org.example.Model.GameMap;

public class MapState implements GameState{
    GameMap map;
    String name = "map";
    @Override
    public void update(GameManager manager) {
        map = manager.gameMap;
    }

    @Override
    public String getName() {
        return name;
    }
}
