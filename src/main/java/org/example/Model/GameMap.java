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
    GameManager manager;

    List<MapObserver> obs = new ArrayList<>();

    ArrayList<String> lvls = new ArrayList<>();
    int currentLvl  = 0;

    GameMap(int dif, User user , GameManager maneger, MapObserver mapObs){
        this.map  = new Graph<>();
        this.opponents = new ArrayList<>();
        this.heimdall = new Opponent(400, 10, 3, "Heimdall");
        this.balder = new Opponent(500*dif, 15, 3, "Balder");
        this.freja = new Opponent(600*dif, 20, 3, "Freja");
        this.tor = new Opponent(1000*dif, 30, 3, "Tor");
        this.oden = new Opponent(1500*dif, 40, 2, "Oden");
        this.opponents.addAll(Arrays.asList(this.heimdall,this.balder,this.freja, this.tor,this.oden));



        obs.add(mapObs);

        this.manager = maneger;
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

        //update list of enemies, index = lvl
        getLvlOps();
    }



    public void setLvlFalse() {
        this.lvlSelected = false;
        //manager.setState(new RoundState());
    }

    public List<Opponent> getNeighbours(Opponent op) {
        return map.neighbours(op);
    }



    public void levelSelect(String s) {
        if (currentOpponent == null) {   currentOpponent = heimdall;
            manager.initRound();}
    for (Opponent op: map.neighbours(currentOpponent)) {
        if (s == op.getName()) {
            currentOpponent = op;
            manager.initRound();
        }
        else if (s == "Shop") {
            initShop();

        }
    }
    }

    private void initShop() {

    }






    public void getLvlOps(){
        List<Opponent> ops =  map.getAllVertices();

        for (Opponent o: ops) {
            String name = o.getName();
            System.out.println(name);
            lvls.add(name);
        }
    }

    //Method called after a round has benn won
    //Updates with new visible enemies.
    public void updateMap( ){
        //Current Opponent is complete



       notifyMapUpdate();
    }

    public Graph<Opponent> getMap() {
        return this.map;
    }

    public void notifyMapUpdate() {
        for(MapObserver o: obs) {
            o.onMapChanged(lvls, currentLvl);
        }
    }




}
