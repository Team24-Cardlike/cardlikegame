package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.MenuController;
import org.example.Controller.RoundController;
import org.example.Model.*;
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
        RoundView rview = new RoundView();
        GameManager manager = new GameManager(rview);
        rview.setGameManager(manager);        
        Save save = new Save(new GameData(manager));
        RoundController roundController = new RoundController(manager.currentRound, manager, save);


        MainMenuView mview = new MainMenuView();
        mview.setGameManager(manager);
        MenuController menuController = new MenuController(manager);

        ShopView sview = new ShopView();
        ShopController shopController = new ShopController(manager.getUser(), manager);
        sview.setShopController(shopController);
        GameRender gameRender = new GameRender(rview, mview, sview);

        manager.setStateObs(gameRender);
        mview.setController(menuController);
        rview.setController(roundController);

        new Lwjgl3Application(gameRender, config);
    }
}
