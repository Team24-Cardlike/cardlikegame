package org.example.Model.OpponentFactories;

public abstract class RegularOpponent extends Opponent{



    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public String getTexture() {
        return "";
    }

    @Override
    public void takeDamage(int damage){
        health = health-damage;
    }

    @Override
    public float getHealthRatio(){
        return ((float) health/maxHealth);
    }

}
