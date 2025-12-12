package org.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import org.example.Controller.MenuController;
import org.example.Model.Round;
import org.example.Model.StateObserver;
import org.example.Views.MapView;
import org.example.Views.RoundView;
import org.example.Controller.RoundController;
import org.example.Model.GameManager;
import org.example.Views.MainMenuView;

public class GameRender extends Game implements StateObserver {

    private final RoundView rview;
    private final MainMenuView mview;
    private final MapView mapView;
    private Screen current;



    GameRender(RoundView rview, MainMenuView mview, MapView mapView) {
        //this.round = round;
        this.mapView = mapView;
        this.rview = rview;
        this.mview = mview;

    }
    @Override
    public void create() {
        setScreen(mview);
    }


    public void switchView(String state) {
        switch (state) {
            case "round":
                setScreen(rview);

                break;
            case "map":
                setScreen(mapView);
                break;
            case "shop":
                break;
            case "menu":
                setScreen(mview);
                break;
        }
    }

    @Override
    public void updateState(String state) {
        switchView(state);
    }
}
