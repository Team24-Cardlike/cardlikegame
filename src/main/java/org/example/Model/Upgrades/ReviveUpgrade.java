package org.example.Model.Upgrades;

public class ReviveUpgrade<T extends Number> extends Upgrade{
    ReviveUpgrade(String name, String desc, int cost) {
        super(name, desc, cost, "Sustain");
    }

    @Override
    public T getNum() {
        T t = null;
        return t;
    }
}
