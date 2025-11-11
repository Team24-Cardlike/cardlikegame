import java.util.ArrayList;

public abstract class Player {
    public int maxHealth;
    public int health;
    abstract int getDamage(Object context);
    int getHealth(Player player){
        return player.health;
    }

}
