package org.example.Model.OpponentFactories;

public abstract class BossOpponent implements OpponentInterface {
    protected String name;
    protected int maxHealth;
    public int damage;
    public int turns;
    public String image;
    public int health;

    public abstract void Create();


    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public String getTexture() {
        return "";
    }

    public String getName() {

        return this.name;

    }

    @Override
    public void takeDamage(int damage) {
        this.health = this.health - damage;

    }

    @Override
    public float getHealthRatio() {
        return  ((float)this.health/this.maxHealth);
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
   public void reset() {
        this.health = this.maxHealth;
    }
}

