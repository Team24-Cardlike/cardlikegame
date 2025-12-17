package org.example.Model.OpponentFactories;


public abstract class Opponent implements OpponentInterface {
    int health;

    public int getDamage() {
        return 0;
    }

    public String getTexture() {
        return "";
    }

    public String getName() {
        return "";
    }

    public void takeDamage(int damage) {

    }

    public float getHealthRatio() {
        return 0;
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int health){
        this.health = health;
    }

}
