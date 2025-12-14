package org.example.Model.GameState;

import org.example.Model.GameManager;

public interface GameState {
void update(GameManager manager);
String getName();
}
