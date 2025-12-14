package org.example.Model;

// import java.util.ArrayList;

public abstract class Player {
    public int maxHealth;
    int health;
    abstract int getDamage();

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    void takeDamage(int damage){
        this.health-=damage;
    }

    public float getHealthRatio(){
        return (float) health/maxHealth;
    }


}
