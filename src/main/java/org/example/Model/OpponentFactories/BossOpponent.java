package org.example.Model.OpponentFactories;

public abstract class BossOpponent extends Opponent {

    public BossOpponent(String name, int maxHealth, int damage, int turns, String image){
        super(name, maxHealth, damage, turns, image);
    }
}

