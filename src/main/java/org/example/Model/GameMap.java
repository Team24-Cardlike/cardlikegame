package org.example.Model;


import org.example.Model.GameState.RoundState;

import org.example.Model.OpponentFactories.*;

import java.util.*;
public class GameMap {
    Graph<Opponent> map;
    ArrayList<BossOpponent> opponents;
    BossOpponent heimdall;
    BossOpponent balder;
    BossOpponent freja;
    BossOpponent tyr;
    BossOpponent tor;
    BossOpponent oden;
    Opponent currentOpponent;
    boolean lvlSelected = false;
    GameManager manager;
    BossFactory bf = new BossFactory();
    RegularFactory rf = new RegularFactory();


    private List<MapObserver> obs = new ArrayList<>();
    private Set<String> availableLvls = new HashSet<>();
    private Set<String> completedLvls = new HashSet<>();
    private ArrayList<String> lvls = new ArrayList<>();


    GameMap(int dif, GameManager manager, MapObserver mapObs){
        this.map  = new Graph<>();
        this.opponents = new ArrayList<>();
        this.heimdall = bf.Create("Heimdall");
        this.balder = bf.Create("Balder");
        this.freja = bf.Create("Freja");
        this.tyr = bf.Create("Tyr");
        this.tor = bf.Create("Tor");
        this.oden = bf.Create("Oden");


        this.opponents.addAll(Arrays.asList(this.heimdall,this.balder,this.freja, this.tor,this.oden));

        obs.add(mapObs);

        this.manager = manager;
        createMap();
    }

    void createMap(){

        //Add opponents to nodes
        map.addVertex(heimdall);
        map.addVertex(balder);
        map.addVertex(freja);
        map.addVertex(tyr);
        map.addVertex(tor);
        map.addVertex(oden);

        //Attach nodes
        map.addEdge(heimdall, balder, false);

        map.addEdge(balder, tor, false);
        map.addEdge(balder, freja, false);


        map.addEdge(freja, tyr, false);
        map.addEdge(tyr, tor, false);

        map.addEdge(tor, oden, false);

        //update list of enemies, index = lvl
        getLvlOps();
        this.availableLvls.add(heimdall.getName());

    }




    public List<Opponent> getNeighbours(Opponent op) {
        return map.neighbours(op);
    }

    public ArrayList<String> getLvls(){
        return lvls;
    }

    public void levelSelect(String s) {
        //Setting first lvl
        if (currentOpponent == null &&  s.equals(heimdall.getName()) ) {   currentOpponent =  heimdall;
            manager.initRound();
        }

        else if (s.equals("Shop")) {
            initShop();
            return;
        }
        for (Opponent op: getNeighbours(currentOpponent)){//map.neighbours(currentOpponent)) {
            if (Objects.equals(s, op.getName())) {
                currentOpponent = op;
                manager.initRound();
                return;
            }
        }
    }

    private void initShop() {

    }

    public void getLvlOps(){
        lvls.clear();
        List<Opponent> ops = map.getAllVertices(heimdall);

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
