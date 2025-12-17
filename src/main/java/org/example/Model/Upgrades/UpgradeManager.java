package org.example.Model.Upgrades;

import org.example.Model.Round;
import org.example.Model.RoundObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpgradeManager {
    UpgradeLibrary lib = new UpgradeLibrary();

    public void checkUpgrade(Upgrade upgrade, Round roundState){
        if(upgrade.checkCondition(roundState) && roundState.getUser().upgrades.contains(upgrade)){
            upgrade.onTriggered(roundState);
        }


        /*if (Objects.equals(upgrade, lib.getUpgrade("Fatality")) && round.getOpponent().getHealthRatio()<=(float)upgrade.getNum()){
            return true;
        }
        else{
            return false;
        }*/
    }
}
