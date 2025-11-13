package src.main.java.org.example.Model;

public class Card {
    public final String suit;
    public final int rank;
    public final String name;
    public final String pic;
    Card(String suit, int rank){
        this.suit = suit;
        this.rank = rank;
        this.name = rank + " of " + suit;
        this.pic = (rank+suit+".png"); //named as suit+rank in files later
    }
}


