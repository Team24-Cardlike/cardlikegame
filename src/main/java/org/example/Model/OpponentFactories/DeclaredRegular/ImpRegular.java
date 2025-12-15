package org.example.Model.OpponentFactories.DeclaredRegular;

import org.example.Model.OpponentFactories.RegularOpponent;

public class ImpRegular extends RegularOpponent {
    @Override
    public void Create() {
        super.name = "Imp";
        super.maxHealth = 200;
        super.damage = 10;
        super.image = name+".png";
    }

    @Override
    public String getName() {
        return name;
    }
}
