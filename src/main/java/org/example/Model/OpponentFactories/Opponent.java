package org.example.Model.OpponentFactories;


public abstract class Opponent implements OpponentInterface {
    String name;
    int maxHealth;
    int damage;
    int turns;
    String image;
    public int health;
    float healthRatio;
    public Opponent(String name, int maxHealth, int damage, int turns, String image){
        this.name = name;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.turns = turns;
        this.image = image;
        setHealth(maxHealth);
    }
    public int getDamage() {
        return this.damage;
    }

    public String getTexture() {
        return this.image;
    }

    public String getName() {
        return this.name;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public float getHealthRatio(){
        if(this.maxHealth == 0){
            return 0f;
        }
        else {
            this.healthRatio = ((float)this.health / (float)this.maxHealth);
            return this.healthRatio;
        }
    }

    public int getHealth(){
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
