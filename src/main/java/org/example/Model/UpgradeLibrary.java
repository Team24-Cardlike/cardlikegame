package org.example.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UpgradeLibrary {
    CardCombos combos = new CardCombos();
    public final List<DamageUpgrade> damageUpgrades = new ArrayList<>();
    public final List<SustainUpgrade> sustainUpgrades = new ArrayList<>();
    public final List<EconomyUpgrade> economyUpgrades = new ArrayList<>();

    public final List<Upgrade> allUpgrades = new ArrayList<>();

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
    }
    private void registerSustainUpgrades() {
        //this.sustainUpgrades.add();
    }
    private void registerEconomyUpgrades() {
        //this.economyUpgrades.add();
    }

    public Upgrade getRandomUpgrade() {
        return allUpgrades.get(new Random().nextInt(allUpgrades.size()));
    }
}