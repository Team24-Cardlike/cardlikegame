package org.example.Model.Upgrades;

import org.example.Model.Round;
import org.example.Model.RoundObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpgradeManager {
    //Kind of an unnecessary middle-step IDK
    public void checkUpgrade(Upgrade upgrade, Round roundState){
        if(upgrade.checkCondition(roundState) && roundState.getUser().getUpgrades().contains(upgrade)) {
            upgrade.onTriggered(roundState);
        }
    }
}
