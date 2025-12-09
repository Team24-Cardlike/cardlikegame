package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.RoundController;
import org.example.Model.*;
import org.example.Views.RoundView;
import org.example.Views.MainMenuView;

//Du är mer av ett problem nu än problemet själv -Kristoffer under roleplaying workshop till Axel :)
//Holy words
import org.example.Controller.RoundController;
//import org.example.GameRender;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(("Maven LibGDX test"));
        config.setWindowedMode(800,600);

        //Opponent opp = new Opponent(2000, 25, 3, "enemyEvil");

        RoundView rview = new RoundView();
        GameManager manager = new GameManager( rview);
        rview.setRound(manager.currentRound);

        //Round round = new Round(opp, ob);
        //RoundView rview = new RoundView();
        //rview.setRound(round);
        MainMenuView mview = new MainMenuView();
        RoundController roundController = new RoundController(manager.currentRound);

        GameRender gameRender = new GameRender(rview,mview, roundController, manager);

        mview.setController(roundController);
        rview.setController(roundController);
        //mview.setController(controller);




        rview.setController(roundController);

        //round.observers.addObserver(rview);
        //round.observers.notifyGameInit();
        new Lwjgl3Application(gameRender, config);
    }
}
