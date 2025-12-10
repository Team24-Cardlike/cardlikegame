package org.example.Model.Upgrades;

public class ExecuteUpgrade<T extends Number> extends Upgrade{
    T threshold;

    /***
     *
     * @param name
     * @param cost
     * @param threshold float "percentage" of HP's execute
     */
    ExecuteUpgrade(String name, int cost, T threshold) {
        super(name, "Executes the opponent when it reaches" + (float)threshold*100 + "% of it's maxhealth.", cost, "Sustain");
        this.threshold = threshold;
    }

    @Override
    public T getNum() {
        return threshold;
    }
}
