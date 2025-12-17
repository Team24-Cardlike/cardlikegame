package org.example.Model.OpponentFactories.DeclaredRegular;

import org.example.Model.OpponentFactories.RegularOpponent;

public class ImpRegular extends RegularOpponent {

    public ImpRegular() {
        super("Imp", 200, 10, 1,"Imp.png");
        setHealth(this.getMaxHealth());
    }
}
