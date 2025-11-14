package org.example.Model;

// import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;

public abstract class Player {
    public int maxHealth;
    public int health;
    abstract int getDamage();

    int getHealth(Player player){
        return player.health;
    }
    void takeDamage(int damage){
        this.health-=damage;
    }

}
