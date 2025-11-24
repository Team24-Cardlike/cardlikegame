package org.example.Model;
import org.w3c.dom.Node;


import java.util.*;
public class GameMap {
    Graph<Game> map;
    ArrayList<Opponent> opponents;
    Opponent heimdall;
    Opponent balder;
    Opponent freja;
    Opponent tor;
    Opponent oden;

    GameMap(int dif){
        this.map  = new Graph<>();
        this.opponents = new ArrayList<>();
        this.heimdall = new Opponent(300*dif, 10, 3, "Heimdall");
        this.balder = new Opponent(500*dif, 15, 3, "Balder");
        this.freja = new Opponent(600*dif, 20, 3, "Freja");
        this.tor = new Opponent(1000*dif, 30, 3, "Tor");
        this.oden = new Opponent(1500*dif, 40, 2, "Oden");
        this.opponents.addAll(Arrays.asList(this.heimdall,this.balder,this.freja, this.tor,this.oden));

        createMap();
    }

    void createMap(){
        this.map.addVertex(new Game(this.opponents.getFirst()));
        this.map.addVertex(new Game(this.opponents.getLast()));
        for(int i = 0; i<opponents.size(); i++) {
            if(!(i+1 > opponents.size()))
                this.map.addEdge(new Game(this.opponents.get(i)), new Game(this.opponents.get(i+1)), false);
        }
    }

    public Graph<Game> getMap() {
        return this.map;
    }
}
