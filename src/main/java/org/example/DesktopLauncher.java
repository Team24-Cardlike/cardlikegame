package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.RoundController;
import org.example.Model.GameManager;
import org.example.Model.RoundObserver;
import org.example.View.RoundView;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(("Maven LibGDX test"));
        config.setWindowedMode(800,600);


        RoundView view = new RoundView();
        GameManager  manager = new GameManager( view);
        view.setRound(manager.currentRound);

        RoundController roundController = new RoundController(manager.currentRound);
        GameRender gameRender = new GameRender( view, roundController,manager);

        view.setController(roundController);
        

        new Lwjgl3Application(gameRender, config);

    }
}
