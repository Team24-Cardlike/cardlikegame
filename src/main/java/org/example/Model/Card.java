package org.example.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Card {
    public final String suit;
    public final int rank;
    public final String name;
    public final String pic;

    public Card(String suit, int rank){
        this.suit = suit;
        this.rank = rank;
        this.name = rank + " of " + suit;
        this.pic = (rank+suit+".png"); //named as suit+rank in files later
    }

    public Texture getCardTexture(){return new Texture(pic);}


}


