package org.example.Model.OpponentFactories;

import org.example.Model.OpponentFactories.DeclaredBosses.*;

public class BossFactory extends OpponentFactory {
    @Override
    public BossOpponent Create(String name) {
        BossOpponent boss;
        switch(name){
            case "Heimdall":
                boss = new HeimdallBoss();
                boss.Create();
                return boss;
            case "Balder":
                boss = new BalderBoss();
                boss.Create();
                return boss;
            case "Freja":
                boss = new FrejaBoss();
                boss.Create();
                return boss;
            case "Tyr":
                boss = new TyrBoss();
                boss.Create();
                return boss;
            case "Tor":
                boss = new TorBoss();
                boss.Create();
                return boss;
            case "Oden":
                boss = new OdenBoss();
                boss.Create();
                return boss;
        }
        return null;
    }
}
