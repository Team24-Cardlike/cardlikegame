package org.example.Model.OpponentFactories;

import org.example.Model.Player;

public interface OpponentInterface {

    public int getDamage();

    public String getTexture();

    public String getName();

    public void takeDamage(int damage);

    float getHealthRatio();

    int getHealth();

    void reset();
}
