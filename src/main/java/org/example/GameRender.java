package org.example;

import com.badlogic.gdx.Game;
import org.example.Model.StateObserver;
import org.example.Views.MapView;
import org.example.Views.HandbookView;
import org.example.Views.RoundView;
import org.example.Views.MainMenuView;
import org.example.Views.ShopView;
import org.example.Views.Music.BackgroundMusic;

public class GameRender extends Game implements StateObserver {
    private RoundView roundView;
    private MainMenuView menuView;
    private MapView mapView;    
    private ShopView shopView;
    private HandbookView handbookView;

    GameRender(RoundView roundView, MainMenuView menuView, ShopView shopView, HandbookView handbookView, MapView mapview) {        
        this.roundView = roundView;
        this.menuView = menuView;
        this.shopView = shopView;
        this.handbookView = handbookView;
        this.mapView = mapview;
    }

    @Override
    public void create() {
        setScreen(menuView);
        new BackgroundMusic("assets/music/Valheim OST - Plains.mp3");
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
