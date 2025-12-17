package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class BalderBoss extends BossOpponent {

    public BalderBoss() {
        super("Balder", 500, 15, 3, "Balder.png");
        super.setHealth(getMaxHealth());
    }
}
