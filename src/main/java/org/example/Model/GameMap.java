package org.example.Model;
import org.example.Model.OpponentFactories.*;

import java.util.*;
public class GameMap {
    Graph<Opponent> map;
    ArrayList<BossOpponent> BossOpponents;
    ArrayList<RegularOpponent> RegularOpponents;
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


    GameMap(int dif, GameManager manager, MapObserver mapObs){
        this.map  = new Graph<>();
        this.BossOpponents = new ArrayList<>();
        this.RegularOpponents = new ArrayList<>();
        this.heimdall = bf.Create("Heimdall");
        this.balder = bf.Create("Balder");
        this.freja = bf.Create("Freja");
        this.tyr = bf.Create("Tyr");
        this.tor = bf.Create("Tor");
        this.oden = bf.Create("Oden");
        this.imp1 = rf.Create("Imp");
        this.wolf = rf.Create("Wolf");
        this.gnome = rf.Create("Gnome");
        this.imp2 = rf.Create("Imp");


        this.BossOpponents.addAll(Arrays.asList(this.heimdall,this.balder,this.freja, this.tor,this.oden));
        this.RegularOpponents.addAll(Arrays.asList(this.imp1,this.imp2,this.gnome,this.wolf));
        obs.add(mapObs);

        this.manager = manager;
        this.shop = new Shop(manager);
        createMap();
    }

    void createMap(){        

         //Add BossOpponents to nodes
        map.addVertex(heimdall);
        map.addVertex(balder);
        map.addVertex(freja);
        map.addVertex(tyr);
        map.addVertex(tor);
        map.addVertex(oden);

        //Add RegularOpponents to nodes
        map.addVertex(imp1);
        map.addVertex(imp2);
        map.addVertex(wolf);
        map.addVertex(gnome);

        //Attach nodes
        map.addEdge(heimdall, wolf, false);

        map.addEdge(wolf,balder,false);
        map.addEdge(wolf, tyr,false);


        map.addEdge(balder,imp2,false);

        map.addEdge(tyr, gnome, false);
        map.addEdge(imp2, freja, false);

        map.addEdge(freja,gnome,false);

        map.addEdge(gnome, imp1, false);


        map.addEdge(imp1, tor, false);

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
    

    // public void setLvlFalse() {
    //     this.lvlSelected = false;
    //     manager.setState(new RoundState());
    // }

    public List<Opponent> getNeighbours(Opponent op) {
        return map.neighbours(op);
    }

    public ArrayList<String> getLvls(){
        return lvls;
    }

    public void levelSelect(String s) {    
                        
        for (int i = 0; i < RegularOpponents.size(); i++) {            
            Opponent op = RegularOpponents.get(i);       
            if (s == op.getName()) System.out.println(map.neighbours(op) + " " + s);     
            System.out.println(availableLvls + " " + completedLvls);    
            if (s == op.getName() && availableLvls.contains(op.getName()) && !completedLvls.contains(op.getName())) {                                                        
                currentOpponent = op;                             
                manager.initRound();
            }
            else if (s == "Shop") {
                initShop();                                    
            }
        }

        for (int i = 0; i < BossOpponents.size(); i++) {            
            Opponent op = BossOpponents.get(i);       
            if (s == op.getName()) System.out.println(map.neighbours(op) + " " + s);     
            System.out.println(availableLvls + " " + completedLvls);
            if (s == op.getName() && availableLvls.contains(op.getName()) && !completedLvls.contains(op.getName())) {                                                        
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
            availableLvls.add(op.getName());}
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
        for(MapObserver o: obs) {
            o.onMapChanged(this.lvls,this.completedLvls, this.availableLvls);
        }
    }
    


}
