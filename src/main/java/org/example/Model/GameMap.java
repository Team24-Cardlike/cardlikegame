package org.example.Model;
import org.example.Model.OpponentFactories.*;

import java.util.*;
public class GameMap {
    Graph<Opponent> map;
    ArrayList<BossOpponent> bossOpponents;
    ArrayList<RegularOpponent> regularOpponents;
    BossOpponent heimdall;
    BossOpponent balder;
    BossOpponent freja;
    BossOpponent tyr;
    BossOpponent tor;
    BossOpponent oden;
    RegularOpponent gnome;
    RegularOpponent imp1;
    RegularOpponent imp2;
    RegularOpponent wolf;
    Opponent currentOpponent;

    GameManager manager;
    BossFactory bf = new BossFactory();
    RegularFactory rf = new RegularFactory();
    Shop shop;

    private List<MapObserver> obs = new ArrayList<>();
    private Set<String> availableLvls = new HashSet<>();
    private Set<String> completedLvls = new HashSet<>();
    private ArrayList<String> lvls = new ArrayList<>();


    
    /**
     * Create all opponents and call createMap
     * @param manager
     * @param mapObs
     */
    GameMap(GameManager manager, MapObserver mapObs){
        this.map  = new Graph<>();
        this.bossOpponents = new ArrayList<>();
        this.regularOpponents = new ArrayList<>();
        this.heimdall = bf.Create("Heimdall");
        this.balder = bf.Create("Balder");
        this.freja = bf.Create("Freja");
        this.tyr = bf.Create("Tyr");
        this.tor = bf.Create("Tor");
        this.oden = bf.Create("Oden");
        this.imp1 = rf.Create("Imp");
        this.wolf = rf.Create("Wolf");
        this.gnome = rf.Create("Gnome");
        this.imp2 = rf.Create("Imp2");

        this.bossOpponents.addAll(Arrays.asList(this.heimdall,this.balder,this.freja, this.tor,this.tyr,this.oden));
        this.regularOpponents.addAll(Arrays.asList(this.imp1,this.imp2,this.gnome,this.wolf));

        obs.add(mapObs);

        this.manager = manager;
        this.shop = new Shop(manager);
        createMap();
    }

    public void setDifficulty(String dif){
        float hpModifier;
        float dmgModifier;
        if(Objects.equals(dif, "Hard")){
            hpModifier = 1.5f;
            dmgModifier = 2.0f;
        }
        else if(Objects.equals(dif, "Normal")){
            hpModifier = 1.0f;
            dmgModifier = 1.0f;
        }
        else {
            hpModifier = 0.75f;
            dmgModifier = 0.80f;
        }

        for(BossOpponent boss : this.bossOpponents){
            int newMaxHealth = (int) ((float)boss.getMaxHealth() * hpModifier);
            int newDamage = (int) ((float)boss.getDamage()* dmgModifier);
            boss.setMaxHealth(newMaxHealth);
            boss.setHealth(boss.getMaxHealth());
            boss.setDamage(newDamage);
        }

        for(RegularOpponent reg : this.regularOpponents){
            int newMaxHealth = (int) ((float)reg.getMaxHealth() * hpModifier);
            int newDamage = (int) ((float)reg.getDamage()* dmgModifier);
            reg.setMaxHealth(newMaxHealth);
            reg.setHealth(reg.getMaxHealth());
            reg.setDamage(newDamage);
        }
    }


    /**
     * Add all opponents to map graph
     */
    void createMap(){

         //Add BossOpponents to nodes
        this.map.addVertex(this.heimdall);
        this.map.addVertex(this.balder);
        this.map.addVertex(this.freja);
        this.map.addVertex(this.tyr);
        this.map.addVertex(this.tor);
        this.map.addVertex(this.oden);

        //Add RegularOpponents to nodes
        this.map.addVertex(this.imp1);
        this.map.addVertex(this.imp2);
        this.map.addVertex(this.wolf);
        this.map.addVertex(this.gnome);

        //Attach nodes
        this.map.addEdge(this.heimdall, this.wolf, false);

        this.map.addEdge(this.wolf,this.balder,false);
        this.map.addEdge(this.wolf, this.tyr,false);


        map.addEdge(this.balder,this.imp2,false);

        map.addEdge(this.tyr, this.gnome, false);
        map.addEdge(this.imp2, this.freja, false);

        map.addEdge(this.freja,this.gnome,false);

        map.addEdge(this.gnome, this.imp1, false);


        map.addEdge(this.imp1, this.tor, false);

        map.addEdge(this.tor, this.oden, false);

        //update list of enemies, index = lvl
        getLvlOps();
        this.availableLvls.add(heimdall.getName());

    }

    Set<String> getCompletedLvls() {
        return this.completedLvls;
    }

    Set<String> getAvailableLvls() {
        return this.availableLvls;
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
        

    public List<Opponent> getNeighbours(Opponent op) {
        return this.map.neighbours(op);
    }

    public ArrayList<String> getLvls(){
        return this.lvls;
    }


    /**
     * calls initRound based on where you click
     * @param s name of level clicked
     */
    public void levelSelect(String s) {
        for (int i = 0; i < this.regularOpponents.size(); i++) {
            Opponent op = this.regularOpponents.get(i);
            if (s == op.getName() && this.availableLvls.contains(op.getName()) && !completedLvls.contains(op.getName())) {
                this.currentOpponent = op;
                this.manager.initRound();
            }
            else if (s == "Shop") {
                initShop();                                    
            }
        }

        for (int i = 0; i < this.bossOpponents.size(); i++) {
            Opponent op = this.bossOpponents.get(i);
            if (s == op.getName() && this.availableLvls.contains(op.getName()) && !completedLvls.contains(op.getName())) {
                this.currentOpponent = op;
                this.manager.initRound();
            }
            else if (s == "Shop") {
                initShop();                                    
            }
        }


    }

    private void initShop() {

    }

    public void getLvlOps(){
        this.lvls.clear();
        List<Opponent> ops =  this.map.getAllVertices(this.heimdall);

        for (Opponent o: ops) {

            String name = o.getName();
            this.lvls.add(name);
        }
    }

    /**
     * Method called after a round has been won 
     * Updates with new visible enemies.
     */
    public void updateMap( ){
        // Current Opponent is complete        
        if ( this.currentOpponent != null) {
            this.completedLvls.add(this.currentOpponent.getName());
        for(Opponent op: getNeighbours(this.currentOpponent)){
            this.availableLvls.add(op.getName());}
        }
        notifyMapUpdate();
    }

    public Graph<Opponent> getMap() {
        return this.map;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void notifyMapUpdate() {
        for(MapObserver o: this.obs) {
            o.onMapChanged(this.lvls,this.completedLvls, this.availableLvls);
        }
    }
    


}
