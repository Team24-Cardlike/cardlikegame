package org.example.Model;


public class Opponent extends Player {
    private final String name;
    public int damage;
    public int turns;
    public String image;

    public Opponent(int startHealth, int damage, int turns, String name){
        this.maxHealth = startHealth;
        this.damage = damage;
        this.turns = turns;
        this.setHealth(maxHealth);
        this.name = name;
        this.image = name+".png";
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    public String getName(){
        return name;
    }

    public String getTexture(){return "assets/images/"+this.image+"png";}
}
