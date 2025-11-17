package org.example.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Board {
    public final String pic;

    public Board(String pic){
        this.pic = pic;
    }

    public Texture getBoard(){
        System.out.println("assets/images/"+pic+".png");
        return new Texture(Gdx.files.internal("assets/images/"+pic+".png"));
    }
}
