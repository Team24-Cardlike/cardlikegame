package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.MapController;
import org.example.Controller.MenuController;
import org.example.Controller.RoundController;
import org.example.Model.*;
import org.example.Views.MapView;
import org.example.Views.RoundView;
import org.example.Views.MainMenuView;
import org.example.Views.ShopView;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(("Maven LibGDX test"));
        config.setWindowedMode(800,600);

        //Opponent opp = new Opponent(2000, 25, 3, "enemyEvil");

        //Creating View, Controler for round
        RoundView roundView = new RoundView();        
        MapView mapView = new MapView();
        ShopView shopView = new ShopView();
        GameManager manager = new GameManager(roundView,mapView);
        roundView.setGameManager(manager);        
        Save save = new Save(new GameData(manager));
        RoundController roundController = new RoundController(manager.currentRound, manager);

        roundView.setGameManager(manager);        

        MapController mapController = new MapController();
        mapController.setMap(manager.gameMap, save);

        MainMenuView menuView = new MainMenuView();
        menuView.setGameManager(manager);
        MenuController menuController = new MenuController(manager, save);

        GameRender gameRender = new GameRender(roundView,menuView, mapView, shopView);

        manager.setStateObs(gameRender);
        menuView.setController(menuController);
        roundView.setController(roundController);

        mapView.setController(mapController);
        mapView.setManeger(manager);

        new Lwjgl3Application(gameRender, config);
    }
}
