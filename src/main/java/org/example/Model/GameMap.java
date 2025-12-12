package org.example.Model;


import org.example.Model.GameState.RoundState;

import java.util.*;
public class GameMap {
    Graph<Opponent> map;
    ArrayList<Opponent> opponents;
    Opponent heimdall;
    Opponent balder;
    Opponent freja;
    Opponent tor;
    Opponent oden;
    Opponent currentOpponent;
    boolean lvlSelected = false;
    GameManager maneger;

    GameMap(int dif, User user , GameManager maneger){
        this.map  = new Graph<>();
        this.opponents = new ArrayList<>();
        this.heimdall = new Opponent(300*dif, 10, 3, "Heimdall");
        this.balder = new Opponent(500*dif, 15, 3, "Balder");
        this.freja = new Opponent(600*dif, 20, 3, "Freja");
        this.tor = new Opponent(1000*dif, 30, 3, "Tor");
        this.oden = new Opponent(1500*dif, 40, 2, "Oden");
        this.opponents.addAll(Arrays.asList(this.heimdall,this.balder,this.freja, this.tor,this.oden));

        this.maneger = maneger;
        createMap();
    }

    void createMap(){
        //Add opponents to nodes
        map.addVertex(heimdall);
        map.addVertex(balder);
        map.addVertex(freja);
        map.addVertex(tor);
        map.addVertex(oden);

        //Attach nodes
        map.addEdge(heimdall, balder, false);
        map.addEdge(heimdall, freja, false);

        map.addEdge(balder, tor, false);
        map.addEdge(freja, tor, false);

        map.addEdge(tor, oden, false);
    }



    public void setLvlFalse() {
        this.lvlSelected = false;
        //maneger.setState(new RoundState());
    }

    public void levelSelect(int index) {
        // List<Opponent> list = m

    }

    public Graph<Opponent> getMap() {
        return this.map;}
}
