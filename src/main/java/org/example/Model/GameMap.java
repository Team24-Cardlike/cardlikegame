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
    private int oppIdx;
    boolean lvlSelected = false;
    GameManager manager;

    private List<MapObserver> obs = new ArrayList<>();
    private Set<String> availableLvls = new HashSet<>();
    private Set<String> completedLvls = new HashSet<>();
    private ArrayList<String> lvls = new ArrayList<>();
    int nextLvl  = 0;


    GameMap(int dif, GameManager manager, MapObserver mapObs){
        this.map  = new Graph<>();
        this.opponents = new ArrayList<>();
        this.heimdall = new Opponent(40, 10, 3, "Heimdall");
        this.balder = new Opponent(1*dif, 15, 3, "Balder");

        this.freja = new Opponent(1*dif, 20, 3, "Freja"); // 600
        this.tor = new Opponent(1*dif, 30, 3, "Tor"); // 1000
        this.oden = new Opponent(1*dif, 40, 2, "Oden"); // 1500
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

    Set<String> getCompletedLvls() {
        return completedLvls;
    }

    Set<String> getAvailableLvls() {
        return availableLvls;
    }

    void setCompletedLvls(Set<String> completedLvls) {
        if (completedLvls == null) return;                 
        this.completedLvls = completedLvls;
        notifyMapUpdate(); // ?
    }

    void setAvailableLvls(Set<String> availableLvls) {
        if (availableLvls == null) return;        
        this.availableLvls = availableLvls;
        notifyMapUpdate(); // ?
    }
    

    public void setLvlFalse() {
        this.lvlSelected = false;
        manager.setState(new RoundState());
    }

    public List<Opponent> getNeighbours(Opponent op) {
        return map.neighbours(op);
    }



    public void levelSelect(String s) {   
        if (currentOpponent == null &&  s.equals(heimdall.getName()) ) {   
            currentOpponent = heimdall;
            manager.initRound();
        }                
        // currentOpponent and the opp might be different objects        
        // for (Opponent op: map.neighbours(opponents.get(oppIdx))) { // currentOpponent  
        for (int i = 0; i < opponents.size(); i++) {            
            Opponent op = opponents.get(i);
            if (map.neighbours(opponents.get(oppIdx)).contains(op)) {

                if (s == op.getName()) {                
                    currentOpponent = op;
                    oppIdx = i;        
                    manager.initRound();
                }
                else if (s == "Shop") {
                    initShop();
                    
                }
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

    // Method called after a round has been won
    // Updates with new visible enemies.
    public void updateMap( ){
        // Current Opponent is complete
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

    public void setOppIdx(int oppIdx) {
        this.oppIdx = oppIdx;
    }
    public int getOppIdx() {
        return this.oppIdx;
    }




}
