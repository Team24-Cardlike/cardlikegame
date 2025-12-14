package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.MenuController;
import org.example.Controller.RoundController;
import org.example.Model.*;
import org.example.Views.RoundView;
import org.example.Views.MainMenuView;

//Du är mer av ett problem nu än problemet själv -Kristoffer under roleplaying workshop till Axel :)
//Holy words
import org.example.Controller.RoundController;
import org.example.Views.ShopView;
//import org.example.GameRender;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(("Maven LibGDX test"));
        config.setWindowedMode(1280,720);

        //Opponent opp = new Opponent(2000, 25, 3, "enemyEvil");

        //Creating View, Controler for round
        RoundView roundView = new RoundView();
        MapView mapView = new MapView();

        GameManager manager = new GameManager(roundView,mapView);
        roundView.setGameManager(manager);
        RoundController roundController = new RoundController(manager);

        MapController mapController = new MapController();
        mapController.setMap(manager.gameMap);

        MainMenuView menuView = new MainMenuView();
        menuView.setGameManager(manager);
        MenuController menuController = new MenuController(manager);

        HandbookView hview = new HandbookView();
        ShopView shopView = new ShopView();
        ShopController shopController = new ShopController(manager.getUser(), manager);
        hview.setController(shopController);
        shopView.setShopController(shopController);
        GameRender gameRender = new GameRender(roundView, menuView,mapView, shopView);

        manager.setStateObs(gameRender);
        menuView.setController(menuController);
        roundView.setController(roundController);

        mapView.setController(mapController);
        mapView.setManeger(manager);

        new Lwjgl3Application(gameRender, config);
    }
}
