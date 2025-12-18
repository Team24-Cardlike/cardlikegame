package org.example.Model.OpponentFactories;

import org.example.Model.OpponentFactories.DeclaredBosses.*;
import org.example.Model.OpponentFactories.DeclaredRegular.GnomeRegular;
import org.example.Model.OpponentFactories.DeclaredRegular.ImpRegular;
import org.example.Model.OpponentFactories.DeclaredRegular.ImpRegular2;
import org.example.Model.OpponentFactories.DeclaredRegular.WolfRegular;

public class RegularFactory extends OpponentFactory {
    @Override
    public RegularOpponent Create(String name) {
        switch(name){
            case "Wolf":
                return new WolfRegular();
            case "Gnome":
                return new GnomeRegular();
            case "Imp":
                return new ImpRegular();
            case "Imp2":
                return new ImpRegular2();
        }
        return null;
    }
}
