package org.example.Model.OpponentFactories;

public abstract class BossOpponent extends Opponent {
    protected String name;
    protected int maxHealth;
    public int damage;
    public int turns;
    public String image;
    private int health;

    //public abstract void Create();

    @Override
    public void takeDamage(int damage){
        health = health-damage;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public String getTexture() {
        return "";
    }

    @Override
    public float getHealthRatio(){
        return (float) health/maxHealth;
    }
}

