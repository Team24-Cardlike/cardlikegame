package org.example.Model.GameState;

import org.example.Model.GameManager;

public interface GameState {
void update(GameManager maneger);
String getName();
}
