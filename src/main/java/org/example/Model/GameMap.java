package org.example.Model;
import org.w3c.dom.Node;


import java.util.*;
public class GameMap {
    Graph<Game> map;
    GameMap(){
        this.map  = new Graph<>();
    }

    void generateMap(int amountOfFights){
        for(int i = 0; i<amountOfFights; i++){
                this.map.addEdge(new Game(), new Game(), false);
        }
    }
}
