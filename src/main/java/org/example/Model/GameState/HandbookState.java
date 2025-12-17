package org.example.Model.GameState;

import com.badlogic.gdx.Screen;
import org.example.Model.GameManager;
import org.example.Model.GameMap;

public class HandbookState implements GameState{
    String name = "handbook";
    @Override
    public void update(GameManager manager) {}

    @Override
    public String getName() {
        return this.name;
    }
}
