package src.main.java.org.example.Model;

public class ComboLifesteal extends Upgrade{
    final Combo combo;
    final int vampAmount;
    ComboLifesteal(String name, Combo combo, int vampAmount, int cost) {
        super(name,
                "Gain " + vampAmount + " lifesteal on " + combo.name + " combos.",
                cost, "Sustain");
        this.combo = combo;
        this.vampAmount = vampAmount;
    }
}
