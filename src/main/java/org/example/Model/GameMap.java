package org.example.Model;


import org.example.Model.GameState.RoundState;

import java.util.*;
public class GameMap {
    public Graph<Opponent> map;
    private ArrayList<Opponent> opponents;
    private final Opponent heimdall;
    private final  Opponent balder;
    private final Opponent freja;
    private final Opponent tor;
    private final Opponent oden;
    public Opponent currentOpponent;
    boolean lvlSelected = false;
    GameManager maneger;

    private List<MapObserver> obs = new ArrayList<>();
    private Set<String> availableLvls = new HashSet<>();
    private Set<String> completedLvls = new HashSet<>();
    private ArrayList<String> lvls = new ArrayList<>();
    int nextLvl  = 0;


    GameMap(int dif, GameManager maneger, MapObserver mapObs){
        this.map  = new Graph<>();
        this.opponents = new ArrayList<>();
        this.heimdall = new Opponent(40, 10, 3, "Heimdall");
        this.balder = new Opponent(500*dif, 15, 3, "Balder");

        this.freja = new Opponent(600*dif, 20, 3, "Freja");
        this.tor = new Opponent(1000*dif, 30, 3, "Tor");
        this.oden = new Opponent(1500*dif, 40, 2, "Oden");
        this.opponents.addAll(Arrays.asList(this.heimdall,this.balder,this.freja, this.tor,this.oden));




        obs.add(mapObs);

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

        map.addEdge(balder, tor, false);

        map.addEdge(balder, freja, false);
        map.addEdge(freja, tor, false);

        map.addEdge(tor, oden, false);

        //update list of enemies, index = lvl
        getLvlOps();
        this.availableLvls.add(heimdall.getName());
    }



    public void setLvlFalse() {
        this.lvlSelected = false;
        maneger.setState(new RoundState());
    }

    public List<Opponent> getNeighbours(Opponent op) {
        return map.neighbours(op);
    }



    public void levelSelect(String s) {
        if (currentOpponent == null &&  s.equals(heimdall.getName()) ) {   currentOpponent = heimdall;
            maneger.initRound();
        }
    for (Opponent op: map.neighbours(currentOpponent)) {

        if (s == op.getName()) {
            currentOpponent = op;
            maneger.initRound();
        }
        else if (s == "Shop") {
            initShop();

        }
    }
    }

    private void initShop() {

    }






    public void getLvlOps(){
        lvls.clear();
        List<Opponent> ops =  map.getAllVertices(heimdall);

        for (Opponent o: ops) {
            String name = o.getName();
            lvls.add(name);
        }
    }

    //Method called after a round has benn won
    //Updates with new visible enemies.
    public void updateMap( ){
        //Current Opponent is complete
        if ( currentOpponent != null) {
        completedLvls.add(currentOpponent.getName());
        for(Opponent op: getNeighbours(currentOpponent)){
        availableLvls.add(op.getName());}}
       notifyMapUpdate();
    }

    public Graph<Opponent> getMap() {
        return this.map;
    }

    public void notifyMapUpdate() {
        for(MapObserver o: obs) {
            o.onMapChanged(this.lvls,this.completedLvls, this.availableLvls);
        }
    }




}
