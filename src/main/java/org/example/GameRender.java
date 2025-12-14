package org.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import org.example.Controller.MenuController;
import org.example.Model.GameState.*;
import org.example.Model.Round;
import org.example.Model.StateObserver;
import org.example.Views.MapView;
import org.example.Views.HandbookView;
import org.example.Views.RoundView;
import org.example.Controller.RoundController;
import org.example.Model.GameManager;
import org.example.Views.MainMenuView;
import org.example.Views.ShopView;

import java.util.HashMap;
import java.util.Map;

public class GameRender extends Game implements StateObserver {

    private final RoundView roundView;
    private final MainMenuView menuView;
    private final MapView mapView;
    private Screen current;
    private ShopView shopView;
    private HandbookView handbookView;

    GameRender(RoundView roundView, MainMenuView menuView, ShopView shopView, HandbookView handbookView) {
        //this.round = round;
        this.roundView = roundView;
        this.menuView = menuView;
        this.shopView = shopView;
        this.handbookView = handbookView;

    }
    @Override
    public void create() {
        setScreen(menuView);
    }


    public void switchView(String state) {
        switch (state) {
            case "round":
                setScreen(roundView);
                break;
            case "map":
                setScreen(mapView);
                break;
            case "shop":
                setScreen(shopView);
                break;
            case "menu":
                setScreen(menuView);
                break;
            case "handbook":
                setScreen(handbookView);
                break;
        }
    }

    @Override
    public void updateState(String state){
        switchView(state);
    }
}
