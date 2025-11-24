public abstract class Player {
    int maxHealth;
    int health;

    abstract int getDamage(int damage);
    int getHealth(Player player){
        return player.health;
    }
}
