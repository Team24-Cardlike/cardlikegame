package org.example.Model.Upgrades;
import org.example.Model.CardCombos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class UpgradeLibrary {
    ExecuteUpgrade placeholderUpgrade = new ExecuteUpgrade("INVALID",100, 0.10f);
    CardCombos combos = new CardCombos();
    public final List<Upgrade> damageUpgrades = new ArrayList<>();
    public final List<Upgrade> sustainUpgrades = new ArrayList<>();
    public final List<Upgrade> economyUpgrades = new ArrayList<>();

    public final List<Upgrade> allUpgrades = new ArrayList<>();

    public List<Upgrade> ownedUpgrades = new ArrayList<>();

    public UpgradeLibrary() {
        registerDamageUpgrades();
        registerSustainUpgrades();
        registerEconomyUpgrades();

        // Merge all into single list for easy random selection
        this.allUpgrades.addAll(damageUpgrades);
        this.allUpgrades.addAll(sustainUpgrades);
        this.allUpgrades.addAll(economyUpgrades);
    }

    private void registerDamageUpgrades() {
        this.damageUpgrades.add(new ComboDmgAmp("Lone Wolf", combos.single, 4, 15));
        this.damageUpgrades.add(new ComboDmgAmp("Twin Synergy", combos.pair,2, 15));
        this.damageUpgrades.add(new ComboDmgAmp("Three Musketeers", combos.three_of_a_kind, 1.75, 15));
        this.damageUpgrades.add(new ComboDmgAmp("Jhin's Blessing", combos.four_of_a_kind,1.4444, 15));

        this.damageUpgrades.add(new ExecuteUpgrade("Fatality", 15, 0.10f));
    }
    private void registerSustainUpgrades() {
        this.sustainUpgrades.add(new ReviveUpgrade("Helbound Rebirth", "Hel uses her powers to give you another chance. Come back to life with 1HP.", 10));
    }
    private void registerEconomyUpgrades() {
        //this.economyUpgrades.add();
    }

    public Upgrade getRandomUpgrade() {
        return allUpgrades.get(new Random().nextInt(allUpgrades.size()));
    }

    public Upgrade getUpgrade(String name){
        for(Upgrade upgrade : ownedUpgrades){
            if(Objects.equals(upgrade.name, name)){
                return upgrade;
            }
        }
        return this.placeholderUpgrade;
    }

    public void buyUpgrade(Upgrade upgrade){
        this.ownedUpgrades.add(upgrade);
    }
}