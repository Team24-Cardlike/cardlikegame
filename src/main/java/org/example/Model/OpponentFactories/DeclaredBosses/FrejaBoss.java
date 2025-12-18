package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class FrejaBoss extends BossOpponent {

    public FrejaBoss(){
        super.name = "Freja";
        super.maxHealth = 600;
        super.damage = 20;
        super.turns = 3;
        super.image = name+".png";
        super.setHealth(maxHealth);
    }
    @Override
    public String getName() {
        return name;
    }
}