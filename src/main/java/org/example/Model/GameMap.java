package org.example.Model;


import java.util.*;
public class GameMap {
    Graph<Round> map;
    ArrayList<Opponent> opponents;
    Opponent heimdall;
    Opponent balder;
    Opponent freja;
    Opponent tor;
    Opponent oden;
    Round currentRound;
    User user;
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

        this.user = user;
        this.maneger = maneger;
        createMap();


    }

    void createMap(){
        this.map.addVertex(new Round(user,heimdall, maneger.obs));

        this.map.addVertex(new Round(user,oden, maneger.obs));
        for(int i = 0; i<opponents.size(); i++) {
            if(!(i+1 > opponents.size()-1))
                this.map.addEdge(new Round(user,opponents.get(i), maneger.obs), new Round(user,opponents.get(i+1), maneger.obs), false);
        }

    }

    public Graph<Round> getMap() {
        return this.map;
    }
}
