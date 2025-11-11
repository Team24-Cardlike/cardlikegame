import java.util.ArrayList;

public class Opponent extends Player {
    public int damage;
    private int turns;

    Opponent(int startHealth, int damage, int turns){
        this.maxHealth = startHealth;
        this.damage = damage;
        this.turns = turns;
        this.health = maxHealth;
    }

    @Override
    public int getDamage(Object context) {
        return this.damage;
    }
}
