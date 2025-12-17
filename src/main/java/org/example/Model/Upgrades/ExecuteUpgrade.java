package org.example.Model.Upgrades;

import org.example.Model.Round;

public class ExecuteUpgrade extends Upgrade{
    float threshold;

    /***
     *
     * @param name
     * @param threshold float "percentage" of HP's execute
     * @param cost
     */
    ExecuteUpgrade(String name, float threshold, int cost, String pic, int id) {
        super(name, "Executes the opponent when it reaches" + threshold*100 + "% of it's maxhealth.", cost, "Sustain", pic, id);
        if(threshold>1 || threshold < 0){
            throw new IllegalArgumentException("Threshold needs to be between 0.0 and 1.0");
        }
        this.threshold = threshold;
    }

    /*@Override
    public T getNum() {
        return threshold;
    }*/

    @Override
    public boolean checkCondition(Round round) {
        return round.getOpponent().getHealthRatio() <= this.threshold;
    }

    @Override
    public void onTriggered(Round round) {
        System.out.println("Opponent executed at " + round.getOpponent().getHealth() + " HP from " + this.name + "!");
        round.getOpponent().setHealth(0);
    }
}
