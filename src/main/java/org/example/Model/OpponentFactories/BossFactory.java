package org.example.Model.OpponentFactories;

import org.example.Model.OpponentFactories.DeclaredBosses.*;

public class BossFactory extends OpponentFactory {
    @Override
    public BossOpponent Create(String name) {
        switch(name){
            case "Heimdall":
                return new HeimdallBoss();
            case "Balder":
                return new BalderBoss();
            case "Freja":
                return new FrejaBoss();
            case "Tor":
                return new TorBoss();
            case "Oden":
                return new OdenBoss();
        }
        return null;
    }
}
