package org.example.Model.OpponentFactories.DeclaredRegular;

import org.example.Model.OpponentFactories.RegularOpponent;

public class GnomeRegular extends RegularOpponent {
    @Override
    public void Create() {
        super.name = "Gnome";
        super.maxHealth = 200;
        super.damage = 10;
        super.image = name+".png";
        super.setHealth(maxHealth);
    }

    @Override
    public String getName() {
        return name;
    }
}
