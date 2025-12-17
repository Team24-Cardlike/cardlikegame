package org.example.Model.OpponentFactories;


public abstract class Opponent implements OpponentInterface {
    int health;
    protected String name;
    protected int maxHealth;
    public int damage;
    public String image;

    public int getDamage() {
        return damage;
    }

    public String getTexture() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void takeDamage(int damage) {

    }

    public float getHealthRatio() {
        if(health!=0)
            return (float)health/maxHealth;
        return 0;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
