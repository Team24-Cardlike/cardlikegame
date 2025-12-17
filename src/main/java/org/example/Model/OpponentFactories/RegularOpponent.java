package org.example.Model.OpponentFactories;

public abstract class RegularOpponent extends Opponent{
    public RegularOpponent(String name, int maxHealth, int damage, int turns, String image){
        super(name, maxHealth, damage, turns, image);
    }
}
