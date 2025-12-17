package org.example.Model.OpponentFactories.DeclaredRegular;

import org.example.Model.OpponentFactories.RegularOpponent;

public class GnomeRegular extends RegularOpponent {

    public GnomeRegular() {
        super("Gnome", 200, 10, 1, "Gnome.png");
        setHealth(getMaxHealth());
    }
}
