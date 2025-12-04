package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.Controller;
import org.example.Model.Round;
import org.example.Model.Opponent;
import org.example.Views.RoundView;
import org.example.Views.MainMenuView;

//Du är mer av ett problem nu än problemet själv -Kristoffer under roleplaying workshop till Axel :)
//Holy words

public class DesktopLauncher{
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(("Maven LibGDX test"));
        config.setWindowedMode(800,600);

        Opponent opp = new Opponent(2000, 25, 3, "enemyEvil");
        Round round = new Round(opp);
        RoundView rview = new RoundView();
        rview.setRound(round);
        MainMenuView mview = new MainMenuView();
        GameRender gameRender = new GameRender(round, rview,mview);
        Controller controller = new Controller(round, gameRender);

        gameRender.setController(controller);

        rview.setController(controller);
        mview.setController(controller);
        
        round.observers.addObserver(rview);
        round.observers.notifyGameInit();
        new Lwjgl3Application(gameRender, config);
    }

}
