package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.MenuController;
import org.example.Controller.RoundController;
import org.example.Controller.MapController;
import org.example.Controller.ShopController;
import org.example.Model.*;
import org.example.Views.RoundView;
import org.example.Views.MainMenuView;
import org.example.Views.HandbookView;
import org.example.Views.MapView;
import org.example.Views.ShopView;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(("Rouges of Midgaard"));
        config.setWindowedMode(1280,720);

        //Creating View, Controler for round
        RoundView roundView = new RoundView();        
        MapView mapView = new MapView();
        ShopView shopView = new ShopView();
        GameManager manager = new GameManager(roundView,mapView);

        // roundView.setGameManager(manager);        
        Save save = new Save(new GameData(manager));
        RoundController roundController = new RoundController(manager);
              

        MapController mapController = new MapController(manager);
        mapController.setMap(manager.gameMap, save);

        MainMenuView menuView = new MainMenuView();
        // menuView.setGameManager(manager);
        MenuController menuController = new MenuController(manager, save);

        HandbookView handbookView = new HandbookView();        
        ShopController shopController = new ShopController(manager);
        handbookView.setController(roundController);
        shopView.setShopController(shopController);
        GameRender gameRender = new GameRender(roundView, menuView, shopView, handbookView, mapView);

        manager.setStateObs(gameRender);
        menuView.setController(menuController);
        roundView.setController(roundController);

        mapView.setController(mapController);

        new Lwjgl3Application(gameRender, config);
    }
}
