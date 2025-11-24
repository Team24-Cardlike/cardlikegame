public class Opponent extends Player {
    private int damage;
    private int turns;
    Opponent(int health, int damage, int turns){
        this.health = health;
        this.damage = damage;
        this.turns = turns;
    }
    @Override
     int getDamage(int damage) {
        return damage;
    }
}
