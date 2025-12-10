package org.example.Model;


public class Card {
    public final String suit;
    public final int rank;
    public final String name;
    public final String pic;

    public Card(String suit, int rank){
        this.suit = suit;
        this.rank = rank;
        this.name = rank + " of " + suit;
        this.pic = ("cardPics/" + suit+"/" + rank+suit+".png"); //named as suit+rank in files later
    }



}


