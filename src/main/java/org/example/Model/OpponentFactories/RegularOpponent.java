package org.example.Model.OpponentFactories;

public abstract class RegularOpponent extends Opponent{
    protected String name;
    protected int maxHealth;
    public int damage;
    public String image;

    public abstract void Create();


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
        return ((float) health/maxHealth);
    }

}
