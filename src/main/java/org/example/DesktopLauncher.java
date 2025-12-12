package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.MenuController;
import org.example.Controller.RoundController;
import org.example.Model.*;
import org.example.Views.HandbookView;
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
        config.setWindowedMode(800,600);

        //Opponent opp = new Opponent(2000, 25, 3, "enemyEvil");

        //Creating View, Controler for round
        RoundView rview = new RoundView();
        GameManager manager = new GameManager(rview);
        rview.setGameManager(manager);
        RoundController roundController = new RoundController(manager.currentRound, manager);


        MainMenuView mview = new MainMenuView();
        mview.setGameManager(manager);
        MenuController menuController = new MenuController(manager);

        ShopView sview = new ShopView();
        HandbookView hview = new HandbookView();
        ShopController shopController = new ShopController(manager.getUser(), manager);
        sview.setShopController(shopController);
        hview.setController(shopController);
        GameRender gameRender = new GameRender(rview, mview, sview, hview);

        manager.setStateObs(gameRender);
        mview.setController(menuController);
        rview.setController(roundController);

        new Lwjgl3Application(gameRender, config);
    }
}
