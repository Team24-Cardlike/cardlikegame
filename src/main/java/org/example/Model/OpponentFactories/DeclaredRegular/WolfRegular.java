package org.example.Model.OpponentFactories.DeclaredRegular;

import org.example.Model.OpponentFactories.RegularOpponent;

public class WolfRegular extends RegularOpponent {

    public WolfRegular() {
        super("Wolf", 200, 10, 1, "Wolf.png");
        setHealth(getMaxHealth());
    }
}
