package org.example.Model.GameState;

import com.badlogic.gdx.Screen;
import org.example.Model.GameManager;

public interface GameState {
void update(GameManager manager);
String getName();
}
