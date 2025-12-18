package org.example.Model.OpponentFactories;

import org.example.Model.Player;

public interface OpponentInterface {

    public int getDamage();

    public String getImage();

    public String getName();

    public void takeDamage(int damage);

    public float getHealthRatio();

    public int getHealth();
}
