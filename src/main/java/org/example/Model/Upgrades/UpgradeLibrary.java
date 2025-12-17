package org.example.Model.Upgrades;
import org.example.Model.CardCombos;

import java.util.*;

public class UpgradeLibrary {
    CardCombos combos = new CardCombos();
    public final ArrayList<Upgrade> damageUpgrades = new ArrayList<>();
    public final ArrayList<Upgrade> sustainUpgrades = new ArrayList<>();
    public final ArrayList<Upgrade> economyUpgrades = new ArrayList<>();

    public final ArrayList<Upgrade> allUpgrades = new ArrayList<>();

    public ArrayList<Upgrade> ownedUpgrades = new ArrayList<>();

    public UpgradeLibrary() {
        registerDamageUpgrades();
        registerSustainUpgrades();
        registerEconomyUpgrades();

        // Merge all into single list for easy random selection
        registerAllUpgrades();
    }

    private void registerDamageUpgrades() {
        //Coding the IDs by four-numbered sequences int (cxyz)
        // c - The category (1 - damage, 2 - sustain, 3 - economy)
        // x - When to check it (0 - when round starts, 1 - when damaging, 2 - when taking damage, 3 - after damaging ... , 9 - when round ends)
        // yz - number in the sequence 01 -> 99
        // The IDs are also ordered so that the ones that are supposed to be called earliest has the lowest ID

        //On round start:
        this.damageUpgrades.add(new ExtraDmgFirstAttack("Initiative", 100, 20, "initiative", 1101));

        //Before damaging:
        this.damageUpgrades.add(new SuitDamageAmp("Full Moon", "moon", 15, 25, "full_moon", 1102));
        this.damageUpgrades.add(new SuitDamageAmp("Solar Flare", "sun", 15, 25, "solar_flare", 1103));
        this.damageUpgrades.add(new SuitDamageAmp("Tsunami", "water", 15, 25, "tsunami", 1104));
        this.damageUpgrades.add(new SuitDamageAmp("Overgrowth", "tree", 15, 25, "overgrowth", 1105));

        this.damageUpgrades.add(new ComboDmgAmp("Lone Wolf", combos.single, 3f, 15, "lone_wolf", 1106));
        this.damageUpgrades.add(new ComboDmgAmp("Twin Synergy", combos.pair,2.5f, 15, "twin_synergy", 1107));
        this.damageUpgrades.add(new ComboDmgAmp("Three Musketeers", combos.three_of_a_kind, 1.75f, 15, "three_musketeers", 1108));
        this.damageUpgrades.add(new ComboDmgAmp("Jhin's Blessing", combos.four_of_a_kind,1.4444f, 15, "jhins_blessing", 1109));

        //After damaging
        this.damageUpgrades.add(new ExecuteUpgrade("Fatality", 0.10f, 10, "fatality", 1301));
    }
    private void registerSustainUpgrades() {
        //When damaging:
        this.sustainUpgrades.add(new ComboLifesteal("Rampager's Thirst", combos.single, 0.20f, 15, "rampagers_thirst",2301));
        this.sustainUpgrades.add(new ComboLifesteal("Shield-Brothers' Draught", combos.pair, 0.15f, 15, "shieldbrothers_draught",2302));
        this.sustainUpgrades.add(new ComboLifesteal("Warband's Blood Rite", combos.three_of_a_kind, 0.10f, 15, "warbands_blood_rite",2303));
        this.sustainUpgrades.add(new ComboLifesteal("Feast of the Einherjar", combos.four_of_a_kind, 0.03f, 15, "feats_of_the_einherjar",2304));
        //When taking damage:
        this.sustainUpgrades.add(new ReviveUpgrade("Helbound Rebirth", "Hel uses her powers to give you another chance. Come back to life with 1HP.", 10, 20, "helbound_rebirth", 2201));
        //After round is over:

    }
    private void registerEconomyUpgrades() {
        //After round is over:
        this.economyUpgrades.add(new GoldOnKill("Spoils of War", 10, 3, "spoils_of_war",3301));
        this.economyUpgrades.add(new GoldOnOneshot("One Punch Man", 10, 15, "one_punch_man",3302));
    }

    private void registerAllUpgrades(){
        this.allUpgrades.addAll(damageUpgrades);
        this.allUpgrades.addAll(sustainUpgrades);
        this.allUpgrades.addAll(economyUpgrades);
    }

    public Upgrade getRandomUpgrade() {
        return allUpgrades.get(new Random().nextInt(allUpgrades.size()));
    }

    public Upgrade getUpgrade(int id){
        for(Upgrade upgrade : allUpgrades){
            if(Objects.equals(upgrade.idNum, id)){
                return upgrade;
            }
        }
        return new ReviveUpgrade("Upgrade 404", "This upgrade is merely a placeholder. The upgrade you are searching for doesn't exist.", 1, 1, "upgrade_404", 0);
    }

    public void buyUpgrade(Upgrade upgrade){
        this.ownedUpgrades.add(upgrade);
    }
}