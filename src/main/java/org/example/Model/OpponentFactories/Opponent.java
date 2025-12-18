package org.example.Model.OpponentFactories;


public abstract class Opponent implements OpponentInterface {
    int health;
    String name;
    int maxHealth;
    int damage;
    String image;

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(){
        this.image = (this.name + ".png");
    }

    public String getName() {
        return this.name;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public float getHealthRatio() {
        if(this.health!=0)
            return (float)((float)this.health/(float)this.maxHealth);
        return 0;
    }
}
